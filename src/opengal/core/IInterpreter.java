package opengal.core;

import opengal.api.IAction;
import opengal.api.Listener;
import opengal.exceptions.InterpretException;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IInterpreter extends IRuntime {
    /**
     * Execute a single node.
     *
     * @throws InterpretException raises when the interpreter executes node inappropriately.
     */
    void execute();

    /**
     * Add a listener which will be called when execution of current Node Tree finished.
     *
     * @param listener whether it supports single/multiple-listener based on the implementation.
     */
    void onEnd(@NotNull Listener listener);

    /**
     * Add a listener which will be called just before executing any node.
     * <p>
     * {@linkplain IInterpreter#getCurIndex() index} and {@linkplain IInterpreter#getCurNode() current node}
     * have been already set correctly and prepared to execute a node.
     * </p>
     *
     * @param listener whether it supports single/multiple-listener based on the implementation.
     */
    void beforeExecute(@NotNull Listener listener);

    /**
     * Add a listener which will be called just after executing any node.
     * <p>
     * {@linkplain IInterpreter#getCurIndex() index} has been already changed
     * and but hasn't check whether the Node Tree was ended yet.
     * </p>
     *
     * @param listener whether it supports single/multiple-listener based on the implementation.
     */
    void afterExecute(@NotNull Listener listener);

    /**
     * Set the listener which will be called when execution was blocked.
     *
     * @param listener whether it supports single/multiple-listener based on the implementation.
     */
    void onBlocked(@NotNull Listener listener);

    /**
     * Set the listener which will be called when a new object was bound.
     *
     * @param listener whether it supports single/multiple-listener based on the implementation.
     */
    void onBound(@NotNull Listener listener);

    /**
     * Set a constant, invariable, which cannot be changed during execution.
     *
     * @param name  the constant key for access
     * @param value the read-only value
     */
    void uniform(@NotNull String name, @NotNull Object value);

    /**
     * Start execution.
     */
    void start();

    /**
     * @return Whether execution is blocked
     */
    boolean isBlocked();

    /**
     * @return Node Tree
     */
    @Nullable
    NodeTree getTree();

    /**
     * @param tree Node Tree
     */
    void setTree(@Nullable NodeTree tree);

    /**
     * @return current executing node
     */
    @NotNull
    Node getCurNode();

    /**
     * Add an action or overwrite it.
     *
     * @param name   the name of action
     * @param action the callback
     */
    void addAction(@NotNull String name, IAction action);

    /**
     * Remove an action if it exists.
     *
     * @param name the name of action
     */
    void removeAction(@NotNull String name);

    /**
     * Reset all fields of the interpreter.
     */
    void reset();

    /**
     * Reset all states of the interpreter.
     */
    void clearRuntimeStates();

    /**
     * @return whether the execution was finished or hadn't started yet.
     */
    boolean isEnd();

    /**
     * @return current index of execution.
     */
    int getCurIndex();
}
