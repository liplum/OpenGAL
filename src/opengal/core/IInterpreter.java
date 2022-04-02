package opengal.core;

import opengal.api.IAction;
import opengal.api.IOptions;
import opengal.api.IText;
import opengal.api.Listener;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface IInterpreter {
    void next();

    void execute();

    void setTextHandler(@NotNull IText handler);

    void onEnd(@NotNull Listener listener);

    void beforeExecute(@NotNull Listener listener);

    void afterExecute(@NotNull Listener listener);

    void onBound(@NotNull Listener listener);

    void setOptionHandler(@NotNull IOptions handler);

    void uniform(String name, @NotNull Object value);

    @NotNull
    Object getUniform(String name);

    void setInput(@NotNull String name, @NotNull Object obj);

    void setText(int id);

    void jumpTo(@NotNull String blockName);

    void start();

    void setOptionNumber(int count);

    void bind(@NotNull String name);

    void unbind();

    boolean getBool(@NotNull String name);

    void setSelected(int number);

    int getSelected();

    void doAction(@NotNull String actionName, @NotNull Object[] args);

    @Nullable
    Object getInput(@NotNull String name);

    @Nullable Function<String, Object> getMetaTable();

    void setMetaTable(@NotNull Function<String, Object> metaTable);

    @NotNull StoryTree getTree();

    void setTree(@NotNull StoryTree tree);

    @NotNull Node getCurNode();

    @Nullable Object getCurBound();

    void addAction(String name, IAction action);

    int getOptionNumber();

    void reset();

    boolean isEnd();

    void returnBlock();

    int getCurIndex();
}
