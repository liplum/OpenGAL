package opengal.excpetions;

public class CurNodeNullException extends RuntimeException{
    public CurNodeNullException(String message) {
        super(message);
    }

    public CurNodeNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurNodeNullException(Throwable cause) {
        super(cause);
    }

    public CurNodeNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CurNodeNullException() {
    }
}
