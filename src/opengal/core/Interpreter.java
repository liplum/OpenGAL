package opengal.core;

import opengal.api.IAction;
import opengal.api.Listener;
import opengal.exceptions.*;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Interpreter implements IInterpreter {
    @NotNull
    private final Stack<Integer> calls = new Stack<>();
    @NotNull
    private final HashMap<String, IAction> name2Action = new HashMap<>();
    @NotNull
    private final HashMap<String, Object> fields = new HashMap<>();
    @NotNull
    private final HashSet<String> invariants = new HashSet<>();
    @NotNull
    private final HashSet<Listener> endListeners = new HashSet<>();
    @NotNull
    private final HashSet<Listener> blockedListeners = new HashSet<>();
    @NotNull
    private final HashSet<Listener> resumedListeners = new HashSet<>();
    @NotNull
    private final HashSet<Listener> beforeExecutes = new HashSet<>();
    @NotNull
    private final HashSet<Listener> afterExecutes = new HashSet<>();
    @NotNull
    private final HashSet<Listener> boundListeners = new HashSet<>();
    @Nullable
    private Object curBound;
    private boolean isEnd = true;
    private boolean noNext = false;
    private boolean isBlocked = false;
    private NodeTree tree;
    private int index;
    private Node curNode;

    @Override
    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public void execute() {
        if (tree == null) throw new RuntimeException();
        if (isBlocked) return;
        if (index < 0 || index >= tree.getSize())
            throw new InterpretException("Current index " + index + " is out of range [0," + tree.getSize() + "].");
        curNode = tree.get(index);
        for (Listener before : beforeExecutes)
            before.on();
        if (!noNext)
            index++;
        try {
            curNode.operate(this);
        } catch (Exception e) {
            throw new InterpretException("Index at " + index + " " + curNode.toString(), e);
        }
        noNext = false;
        for (Listener after : afterExecutes)
            after.on();
        if (isEnd)
            for (Listener end : endListeners)
                end.on();
    }

    @Override
    public void uniform(@NotNull String name, @NotNull Object value) {
        fields.put(name, value);
        invariants.add(name);
    }

    @Override
    public void beforeExecute(@NotNull Listener listener) {
        beforeExecutes.add(listener);
    }

    @Override
    public void afterExecute(@NotNull Listener listener) {
        afterExecutes.add(listener);
    }

    @Override
    public void onBound(@NotNull Listener listener) {
        boundListeners.add(listener);
    }

    @Override
    public void onEnd(@NotNull Listener listener) {
        endListeners.add(listener);
    }

    @Override
    public void onBlocked(@NotNull Listener listener) {
        blockedListeners.add(listener);
    }

    @Override
    public void onResumed(@NotNull Listener listener) {
        resumedListeners.add(listener);
    }

    @Override
    public void pushIndex() {
        calls.push(index);
    }

    /**
     * Jump to the block name.
     *
     * @param index which index it jumps to
     */
    public void jumpTo(final int index) {
        if (index < 0 && index > tree.getSize()) {
            throw new OverJumpException("index " + index + " is over than maximum " + tree.getSize());
        }
        this.index = index;
        unbind();
        noNext = true;
    }

    @Override
    public void terminate() {
        isEnd = true;
    }

    @Override
    public void blockExecution() {
        if (!isBlocked) {
            isBlocked = true;
            for (Listener block : blockedListeners)
                block.on();
        }
    }

    @Override
    public void resumeExecution() {
        if (isBlocked) {
            isBlocked = false;
            for (Listener resume : resumedListeners)
                resume.on();
        }
    }

    @Override
    public void start() {
        if (tree == null) {
            throw new InterpretException("No tree");
        }
        final Set<String> inputs = tree.getInputs();
        final Set<String> keys = fields.keySet();
        if (!keys.containsAll(inputs)) {
            throw new InputNotGivenException(tree);
        }
        this.index = 0;
        this.isEnd = false;
        calls.clear();
        unbind();
    }

    @Override
    public void rollbackPopIndex() {
        index = calls.pop();
    }

    @Override
    public void popIndex() {
        calls.pop();
    }

    /**
     * bind the target object
     *
     * @param name input name
     * @throws InputNotGivenException raises when this name hasn't been inputted
     */
    public void bind(@NotNull String name) {
        Object bound = fields.get(name);
        if (bound == null) {
            throw new NoSuchValueException(tree, name);
        }
        curBound = bound;
        for (Listener listener : boundListeners)
            listener.on();
    }

    public void unbind() {
        curBound = null;
    }

    public boolean getBool(@NotNull String name) {
        Object o = fields.get(name);
        if (o instanceof Boolean) {
            return (boolean) o;
        } else if (o instanceof Integer) {
            return ((int) o) != 0;
        } else {
            return o != null && o != OpenGAL.Nothing;
        }
    }

    public void doAction(@NotNull String actionName, @NotNull Object[] args) {
        IAction action = name2Action.get(actionName);
        if (action == null) {
            throw new NoSuchActionException(tree, actionName);
        }
        action.invoke(args);
    }

    @Nullable
    public NodeTree getTree() {
        return tree;
    }

    public void setTree(@Nullable NodeTree tree) {
        this.tree = tree;
    }

    @NotNull
    public Node getCurNode() {
        return curNode;
    }

    @Nullable
    public Object getCurBound() {
        return curBound;
    }

    @Override
    public void addAction(@NotNull String name, IAction action) {
        name2Action.put(name, action);
    }

    @Override
    public void removeAction(@NotNull String name) {
        name2Action.remove(name);
    }

    @Override
    public int getCurIndex() {
        return index;
    }

    @Override
    public void set(@NotNull String name, @NotNull Object value) {
        if (invariants.contains(name)) {
            throw new InvariantException(tree, name);
        }
        fields.put(name, value);
    }

    @Override
    public boolean isBlocked() {
        return isBlocked;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @NotNull T get(@NotNull String name) {
        Object v = fields.get(name);
        if (v == null) {
            throw new NoSuchValueException(tree, name);
        }
        return (T) v;
    }

    @Override
    @NotNull
    public Object getNullable(@NotNull String name) {
        Object v = fields.get(name);
        if (v == null) {
            return OpenGAL.Nothing;
        }
        return v;
    }

    @Override
    public void reset() {
        clearRuntimeStates();
        name2Action.clear();
        beforeExecutes.clear();
        afterExecutes.clear();

        resumedListeners.clear();
        blockedListeners.clear();
        endListeners.clear();
        boundListeners.clear();

        tree = null;
    }

    @Override
    public void clearRuntimeStates() {
        calls.clear();
        fields.clear();
        invariants.clear();
        index = 0;
        isBlocked = false;
        isEnd = true;
        curBound = null;
        curNode = null;
    }
}
