package opengal.core;

import opengal.api.IAction;
import opengal.api.Listener;
import opengal.excpetions.InputNotGivenException;
import opengal.excpetions.InvariantException;
import opengal.excpetions.NoSuchActionException;
import opengal.excpetions.NoSuchValueException;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class Interpreter implements IInterpreter {
    private StoryTree tree;
    private int index;
    private final Stack<Integer> calls = new Stack<>();
    private Node curNode;
    @Nullable
    private Listener endListener;
    @Nullable
    private ArrayList<Listener> beforeExecutes;
    @Nullable
    private ArrayList<Listener> afterExecutes;
    @Nullable
    private ArrayList<Listener> onBounds;
    @NotNull
    private final HashMap<String, IAction> name2Action = new HashMap<>();
    @NotNull
    private final HashMap<String, Object> fields = new HashMap<>();
    @NotNull
    private final HashSet<String> invariants = new HashSet<>();
    @Nullable
    private Object curBound;
    @Nullable
    private Function<String, Object> metaTable;
    private boolean isEnd = false;

    public void next() {
        index++;
    }

    @Override
    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public void execute() {
        curNode = tree.get(index);
        if (beforeExecutes != null) {
            for (Listener before : beforeExecutes) {
                before.on();
            }
        }
        curNode.operate(this);
        if (afterExecutes != null) {
            for (Listener after : afterExecutes) {
                after.on();
            }
        }
        if (isEnd && endListener != null) {
            endListener.on();
        }
    }

    @Override
    public void uniform(String name, @NotNull Object value) {
        fields.put(name, value);
        invariants.add(name);
    }

    @Override
    public void beforeExecute(@NotNull Listener listener) {
        if (beforeExecutes == null) {
            beforeExecutes = new ArrayList<>();
        }
        beforeExecutes.add(listener);
    }

    @Override
    public void afterExecute(@NotNull Listener listener) {
        if (afterExecutes == null) {
            afterExecutes = new ArrayList<>();
        }
        afterExecutes.add(listener);
    }

    @Override
    public void onBound(@NotNull Listener listener) {
        if (onBounds == null) {
            onBounds = new ArrayList<>();
        }
        onBounds.add(listener);
    }

    @Override
    public void onEnd(@NotNull Listener listener) {
        endListener = listener;
    }

    /**
     * Jump to the block name.
     *
     * @param index which index it jumps to
     */
    public void jumpTo(final int index) {
        calls.push(index);
        this.index = index;
    }

    @Override
    public void start() {
        final Set<String> inputs = tree.inputs;
        final Set<String> keys = fields.keySet();
        if (inputs != null && !keys.containsAll(inputs)) {
            throw new InputNotGivenException(tree);
        }
        jumpTo(0);
    }

    @Override
    public void returnBlock() {
        if (!calls.empty()) {
            index = calls.pop();
        }
        isEnd = calls.empty();
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
        if (onBounds != null) {
            for (Listener listener : onBounds) {
                listener.on();
            }
        }
    }

    public void unbind() {
        curBound = null;
    }

    public boolean getBool(@NotNull String name) {
        Object o = fields.get(name);
        if (!(o instanceof Boolean) && metaTable != null) {
            o = metaTable.apply(name);
        }
        if (!(o instanceof Boolean)) {
            throw new NoSuchValueException(tree, name);
        }
        return (boolean) o;
    }

    public void doAction(@NotNull String actionName, @NotNull Object[] args) {
        IAction action = name2Action.get(actionName);
        if (action == null) {
            throw new NoSuchActionException(tree, actionName);
        }
        action.invoke(args);
    }

    @Nullable
    public Function<String, Object> getMetaTable() {
        return metaTable;
    }

    public void setMetaTable(@NotNull Function<String, Object> metaTable) {
        this.metaTable = metaTable;
    }

    @NotNull
    public StoryTree getTree() {
        return tree;
    }

    public void setTree(@NotNull StoryTree tree) {
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
    public void addAction(String name, IAction action) {
        name2Action.put(name, action);
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> @NotNull T get(@NotNull String name) {
        Object v = fields.get(name);
        if (v == null && metaTable != null) {
            v = metaTable.apply(name);
        }
        if (v == null) {
            throw new NoSuchValueException(tree, name);
        }
        return (T) v;
    }

    @Override
    public void reset() {
        fields.clear();
        calls.clear();
        name2Action.clear();
        invariants.clear();
        beforeExecutes = null;
        afterExecutes = null;
        endListener = null;
        curBound = null;
        metaTable = null;
        curNode = null;
        tree = null;
        index = 0;
    }
}
