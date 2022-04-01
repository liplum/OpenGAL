package opengal.core;

import opengal.api.IText;
import opengal.excpetions.CurNodeNullException;
import opengal.excpetions.NoValueException;
import opengal.excpetions.NotInputException;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class Interpreter implements IInterpreter {
    private StoryTree tree;
    private int index;
    private StoryNode curNode;
    @NotNull
    private HashSet<IText> textDisplayer = new HashSet<>();
    @NotNull
    private HashMap<String, Object> name2Input = new HashMap<>();
    @NotNull
    private HashMap<String, Object> values = new HashMap<>();
    @Nullable
    private Object curBound;
    @Nullable
    private Function<String, Object> metaTable;
    private int selected;

    public void next() {
        curNode = tree.get(index);
        index++;
    }

    @Override
    public void execute() {
        if (curNode == null) {
            throw new CurNodeNullException("Current node is null.");
        }
        curNode.operate(this);
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
            throw new NoValueException("Uniform " + name + " hasn't been set.");
        }
        return v;
    }

    @Override
    public void addTextHandler(@NotNull IText handler) {
        textDisplayer.add(handler);
    }

    public void setInput(@NotNull String name, @NotNull Object obj) {
        name2Input.put(name, obj);
    }

    public void setText(int id) {
        for (IText handler : textDisplayer) {
            handler.handleTextID(id);
        }
    }

    /**
     * Jump to the block name.
     *
     * @param blockName name
     */
    public void jumpTo(@NotNull String blockName) {
        // Actually, the name will be mapped to the index.
    }

    public void setOptionCount(int count) {

    }

    /**
     * bind the target object
     *
     * @param name input name
     * @throws NotInputException raises when this name hasn't been inputted
     */
    public void bind(@NotNull String name) {
        Object bound = name2Input.get(name);
        if (bound == null) {
            throw new NotInputException(name + " hasn't been bound yet.");
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
            throw new NoValueException("Can't find value of " + name);
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

    }

    @Nullable
    public Object getInput(@NotNull String name) {
        return name2Input.get(name);
    }

    @Nullable
    public Function<String, Object> getMetaTable() {
        return metaTable;
    }

    public void metaTable(@NotNull Function<String, Object> metaTable) {
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
    public void reset() {
        textDisplayer = new HashSet<>();
        name2Input = new HashMap<>();
        values = new HashMap<>();
        curBound = null;
        metaTable = null;
        selected = 0;
        curNode = null;
        tree = null;
        index = 0;
    }
}
