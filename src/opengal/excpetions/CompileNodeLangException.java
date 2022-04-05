package opengal.excpetions;

public class CompileNodeLangException extends RuntimeException{
    public CompileNodeLangException() {
    }

    public CompileNodeLangException(String message) {
        super(message);
    }

    public CompileNodeLangException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileNodeLangException(Throwable cause) {
        super(cause);
    }

    public CompileNodeLangException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
