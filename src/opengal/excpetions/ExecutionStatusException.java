package opengal.excpetions;

public class ExecutionStatusException extends RuntimeException{
    public ExecutionStatusException() {
    }

    public ExecutionStatusException(String message) {
        super(message);
    }
}
