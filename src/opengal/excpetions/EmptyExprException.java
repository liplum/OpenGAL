package opengal.excpetions;

public class EmptyExprException extends RuntimeException{
    public EmptyExprException() {
    }

    public EmptyExprException(String message) {
        super(message);
    }

    public EmptyExprException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyExprException(Throwable cause) {
        super(cause);
    }

    public EmptyExprException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
