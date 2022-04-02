package opengal.core;

import opengal.api.IAction;
import opengal.api.IOptions;
import opengal.api.IText;
import opengal.api.Listener;
import opengal.excpetions.CurNodeNullException;
import opengal.excpetions.NoSuchActionException;
import opengal.excpetions.NoSuchInputException;
import opengal.excpetions.NoSuchValueException;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.function.Function;

public class Interpreter implements IInterpreter {
    private StoryTree tree;
    private int index;
    private final Stack<Integer> calls = new Stack<>();
    private StoryNode curNode;
    @Nullable
    private IText textDisplayer;
    @Nullable
    private Listener endListener;
    @Nullable
    private IOptions optionsHandler;
    @Nullable
    private ArrayList<Listener> beforeExecutes;
    @Nullable
    private ArrayList<Listener> afterExecutes;
    @Nullable
    private ArrayList<Listener> onBounds;
    @NotNull
    private final HashMap<String, Object> name2Input = new HashMap<>();
    @NotNull
    private final HashMap<String, IAction> name2Action = new HashMap<>();
    @NotNull
    private final HashMap<String, Object> values = new HashMap<>();
    @Nullable
    private Object curBound;
    @Nullable
    private Function<String, Object> metaTable;
    private int selected;
    private int optionNumber;
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
        values.put(name, value);
    }

    @Override
    public @NotNull Object getUniform(String name) {
        Object v = values.get(name);
        if (v == null && metaTable != null) {
            v = metaTable.apply(name);
        }
        if (v == null) {
            throw new NoSuchValueException("Uniform " + name + " hasn't been set.");
        }
        return v;
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
    public void setTextHandler(@NotNull IText handler) {
        textDisplayer = handler;
    }

    @Override
    public void onEnd(@NotNull Listener listener) {
        endListener = listener;
    }

    public void setInput(@NotNull String name, @NotNull Object obj) {
        name2Input.put(name, obj);
    }

    public void setText(int id) {
        if (textDisplayer != null) {
            textDisplayer.handleTextID(id);
        }
    }

    /**
     * Jump to the block name.
     *
     * @param blockName name
     */
    public void jumpTo(@NotNull String blockName) {
        // Actually, the name will be mapped to the index.
        int startIndex = tree.getStartIndex(blockName);
        calls.push(index);
        index = startIndex;
    }

    @Override
    public void start() {
        // Actually, the name will be mapped to the index.
        int startIndex = tree.getStartIndex("Main");
        calls.push(startIndex);
        index = startIndex;
    }

    @Override
    public void returnBlock() {
        if (!calls.empty()) {
            index = calls.pop();
        }
        isEnd = calls.empty();
    }

    public void setOptionNumber(int numer) {
        optionNumber = numer;
        if (optionsHandler != null) {
            optionsHandler.handle(numer);
        }
    }

    /**
     * bind the target object
     *
     * @param name input name
     * @throws NoSuchInputException raises when this name hasn't been inputted
     */
    public void bind(@NotNull String name) {
        Object bound = name2Input.get(name);
        if (bound == null) {
            throw new NoSuchInputException(name + " hasn't been bound yet.");
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
        Object o = values.get(name);
        if (!(o instanceof Boolean) && metaTable != null) {
            o = metaTable.apply(name);
        }
        if (!(o instanceof Boolean)) {
            throw new NoSuchValueException("Can't find value of " + name);
        }
        return (boolean) o;
    }

    public void setSelected(int number) {
        selected = number;
    }

    public int getSelected() {
        return selected;
    }

    public void doAction(@NotNull String actionName, @NotNull Object[] args) {
        IAction action = name2Action.get(actionName);
        if (action == null) {
            throw new NoSuchActionException(actionName + " not found.");
        }
        action.invoke(args);
    }

    @Nullable
    public Object getInput(@NotNull String name) {
        return name2Input.get(name);
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
    public StoryNode getCurNode() {
        return curNode;
    }

    @Nullable
    public Object getCurBound() {
        return curBound;
    }

    @Override
    public void setOptionHandler(@NotNull IOptions handler) {
        optionsHandler = handler;
    }

    @Override
    public void addAction(String name, IAction action) {
        name2Action.put(name, action);
    }

    @Override
    public int getOptionNumber() {
        return optionNumber;
    }

    @Override
    public int getCurIndex() {
        return index;
    }

    @Override
    public void reset() {
        name2Input.clear();
        values.clear();
        calls.clear();
        name2Action.clear();
        beforeExecutes = null;
        afterExecutes = null;
        textDisplayer = null;
        endListener = null;
        optionsHandler = null;
        curBound = null;
        metaTable = null;
        curNode = null;
        tree = null;
        selected = 0;
        optionNumber = 0;
        index = 0;
    }
}
