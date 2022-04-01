package opengal.core;

import opengal.api.IText;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface IInterpreter {
    void next();

    void execute();

    void addTextHandler(@NotNull IText handler);

    void uniform(String name, @NotNull Object value);

    @NotNull
    Object getUniform(String name);

    void setInput(@NotNull String name, @NotNull Object obj);

    void setText(int id);

    void jumpTo(@NotNull String blockName);

    void setOptionCount(int count);

    void bind(@NotNull String name);

    void unbind();

    boolean getBool(@NotNull String name);

    void setSelected(int number);

    int getSelected();

    void doAction(@NotNull String actionName, @NotNull Object[] args);

    @Nullable
    Object getInput(@NotNull String name);

    @Nullable Function<String, Object> getMetaTable();

    void metaTable(@NotNull Function<String, Object> metaTable);

    @NotNull StoryTree getTree();

    void setTree(@NotNull StoryTree tree);

    @NotNull StoryNode getCurNode();

    @Nullable Object getCurBound();

    void reset();
}
