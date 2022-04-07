package opengal.excpetions;

public class InterpretException extends RuntimeException{
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
