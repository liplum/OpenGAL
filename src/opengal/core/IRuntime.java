package opengal.core;

import opengal.exceptions.NoSuchActionException;
import opengal.exceptions.NoSuchValueException;
import opengal.exceptions.OverJumpException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IRuntime extends IExpressionReceiver {

    /**
     * Push current index into the call stack.
     */
    void pushIndex();

    /**
     * Jump to {@code index} position.
     * The next execution will start at that point.
     *
     * @param index the position in Node Tree.
     * @throws OverJumpException raises when the index is out of bounds.
     */
    void jumpTo(int index);

    /**
     * Bind the target object.
     *
     * @param name the key of bound object.
     * @throws NoSuchValueException raises when the target doesn't exist.
     */
    void bind(@NotNull String name);

    /**
     * Unbind current object.
     */
    void unbind();

    /**
     * Execute an action with 0 or more arguments.
     *
     * @param actionName the name of action.
     * @param args       the arguments given.
     * @throws NoSuchActionException raises when there's not corresponding action.
     */
    void doAction(@NotNull String actionName, @NotNull Object[] args);

    /**
     * Get the current bound object.
     *
     * @return the object or null if it was unbound or hadn't bound yet.
     */
    @Nullable
    Object getCurBound();

    /**
     * Get a boolean by {@code name}
     * <ul>
     *     <li>It guarantees it can return a boolean.</li>
     *     <li>If the corresponding value is a real boolean, its {@linkplain Boolean#booleanValue() value} will be returned.</li>
     *     <li>if it's an {@linkplain Integer integer}, true will be returned if it isn't 0.</li>
     *     <li>If it hasn't met the above situations, true will returned if it's neither the null nor the {@linkplain OpenGAL#Nothing Nothing}.</li>
     * </ul>
     *
     * @param name the key of a boolean
     * @return a boolean
     */
    boolean getBool(@NotNull String name);

    /**
     * Pop the call stack and set current index as that value.
     */
    void rollbackPopIndex();

    /**
     * Pop the call stack.
     */
    void popIndex();

    /**
     * Get the value or {@link OpenGAL#Nothing}
     *
     * @param name the key
     * @return the value or {@link OpenGAL#Nothing}
     */
    @NotNull Object getNullable(@NotNull String name);

    /**
     * Terminate the execution.
     */
    void terminate();

    /**
     * Block current execution.
     */
    void blockExecution();

    /**
     * Resume current execution.
     */
    void resumeExecution();
}
