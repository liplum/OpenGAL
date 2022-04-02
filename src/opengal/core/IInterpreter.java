package opengal.core;

import opengal.api.IAction;
import opengal.api.Listener;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface IInterpreter {
    void next();

    void execute();

    void onEnd(@NotNull Listener listener);

    void beforeExecute(@NotNull Listener listener);

    void afterExecute(@NotNull Listener listener);

    void onBound(@NotNull Listener listener);

    void uniform(String name, @NotNull Object value);

    void jumpTo(final int index);

    void start();

    void bind(@NotNull String name);

    void unbind();

    @Nullable
    Object getCurBound();

    boolean getBool(@NotNull String name);

    void doAction(@NotNull String actionName, @NotNull Object[] args);

    @Nullable
    Function<String, Object> getMetaTable();

    void setMetaTable(@NotNull Function<String, Object> metaTable);

    @NotNull
    StoryTree getTree();

    void setTree(@NotNull StoryTree tree);

    @NotNull
    Node getCurNode();

    void addAction(String name, IAction action);

    void reset();

    boolean isEnd();

    void returnBlock();

    int getCurIndex();

    void set(@NotNull String name,@NotNull Object value);

    @NotNull
    <T> T get(@NotNull String name);
}
