package opengal.excpetions;

public class NodeLangException extends RuntimeException{
    public NodeLangException(String message) {
        super(message);
    }

    public NodeLangException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeLangException(Throwable cause) {
        super(cause);
    }

    public NodeLangException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NodeLangException() {
    }
}
