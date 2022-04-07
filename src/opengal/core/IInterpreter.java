package opengal.core;

import opengal.api.IAction;
import opengal.api.Listener;
import opengal.excpetions.NoSuchValueException;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IInterpreter {
    void execute();

    void onEnd(@NotNull Listener listener);

    void beforeExecute(@NotNull Listener listener);

    void afterExecute(@NotNull Listener listener);

    void onBlocked(@NotNull Listener listener);

    void onBound(@NotNull Listener listener);

    void uniform(@NotNull String name, @NotNull Object value);

    void pushIndex();

    void jumpTo(final int index);

    void start();

    void bind(@NotNull String name);

    void unbind();

    @Nullable
    Object getCurBound();

    boolean getBool(@NotNull String name);

    void doAction(@NotNull String actionName, @NotNull Object[] args);

    @NotNull
    NodeTree getTree();

    void setTree(@NotNull NodeTree tree);

    @NotNull
    Node getCurNode();

    void addAction(@NotNull String name, IAction action);

    /**
     * Reset all fields of the interpreter.
     */
    void reset();

    /**
     * Reset all states of the interpreter.
     */
    void clearRuntimeStates();

    boolean isEnd();

    void rollbackPopIndex();

    void popIndex();

    int getCurIndex();

    void set(@NotNull String name, @NotNull Object value);

    /**
     * Gets the existed value
     *
     * @param name the key
     * @param <T>  which type you want (NOTE: not guarantee it's)
     * @return the value
     * @throws ClassCastException   raises if the value doesn't match the type
     * @throws NoSuchValueException raises if the value doesn't exist
     */
    @NotNull <T> T get(@NotNull String name);

    void terminate();

    void blockExecute();

    void continueExecute();
}
