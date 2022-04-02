package opengal.excpetions;

import opengal.core.StoryTree;

public class NoSuchValueException extends RuntimeException {
    public NoSuchValueException(String message) {
        super(message);
    }

    public NoSuchValueException(StoryTree tree, String message) {
        super(
                tree.fileName != null ?
                        message + " in " + tree.fileName :
                        message
        );
    }
}
