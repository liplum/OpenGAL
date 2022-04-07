package opengal.excpetions;

public class CompileNodeException extends RuntimeException {
    public CompileNodeException() {
    }

    public CompileNodeException(String message) {
        super(message);
    }

    public CompileNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileNodeException(Throwable cause) {
        super(cause);
    }

    public CompileNodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
