package opengal.exceptions;

import opengal.tree.Node;
/**
 * Thrown when the interpreter attempts to behave illegally.
 * These include:
 * <ul>
 *     <li>Attempting to start without Node Tree</li>
 *     <li>{@link Node#operate(opengal.core.IRuntime)} throws exception.</li>
 *     <li>Accessing the Node Tree with an out of bounds index.</li>
 * </ul>
 * <p>
 *     It may contain the ture cause of this exception.
 * </p>
 */
public class InterpretException extends RuntimeException {
    public InterpretException() {
    }

    public InterpretException(String message) {
        super(message);
    }

    public InterpretException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterpretException(Throwable cause) {
        super(cause);
    }

    public InterpretException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
