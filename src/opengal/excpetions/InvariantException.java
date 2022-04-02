package opengal.excpetions;

import opengal.core.StoryTree;

public class InvariantException extends RuntimeException{
    public InvariantException(String message) {
        super(message);
    }
    public InvariantException(StoryTree tree, String message) {
        super(
                tree.fileName != null ?
                        message + " in " + tree.fileName :
                        message
        );
    }
}
