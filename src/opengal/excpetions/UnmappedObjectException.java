package opengal.excpetions;

public class UnmappedObjectException extends RuntimeException {
    public UnmappedObjectException() {
    }

    public UnmappedObjectException(String message) {
        super(message);
    }
}
