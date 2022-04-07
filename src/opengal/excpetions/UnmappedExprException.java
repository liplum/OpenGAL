package opengal.excpetions;

public class UnmappedExprException extends RuntimeException {
    public UnmappedExprException() {
    }

    public UnmappedExprException(String message) {
        super(message);
    }
}
