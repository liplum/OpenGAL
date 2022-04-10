package opengal.exceptions;

public class InterpretNullException extends InterpretException {
    public InterpretNullException(String message) {
        super(message);
    }

    public InterpretNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterpretNullException(Throwable cause) {
        super(cause);
    }

    public InterpretNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InterpretNullException() {
    }
}
