package opengal.exceptions;

public class ExecutionStatusException extends InterpretException {
    public ExecutionStatusException() {
    }

    public ExecutionStatusException(String message) {
        super(message);
    }

    public ExecutionStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutionStatusException(Throwable cause) {
        super(cause);
    }

    public ExecutionStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
