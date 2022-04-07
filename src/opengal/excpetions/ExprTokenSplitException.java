package opengal.excpetions;

public class ExprTokenSplitException extends RuntimeException{
    public ExprTokenSplitException() {
    }

    public ExprTokenSplitException(String message) {
        super(message);
    }

    public ExprTokenSplitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExprTokenSplitException(Throwable cause) {
        super(cause);
    }

    public ExprTokenSplitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
