package opengal.core;

import opengal.exceptions.NoSuchValueException;
import org.jetbrains.annotations.NotNull;

public interface IExpressionReceiver {
    void set(@NotNull String name, @NotNull Object value);

    /**
     * Get the existed value
     *
     * @param name the key
     * @param <T>  which type you want (NOTE: not guarantee it's)
     * @return the value
     * @throws ClassCastException   raises if the value doesn't match the given type
     * @throws NoSuchValueException raises if the value doesn't exist
     */
    @NotNull <T> T get(@NotNull String name);
}
