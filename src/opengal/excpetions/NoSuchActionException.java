package opengal.excpetions;

import opengal.core.StoryTree;

public class NoSuchActionException extends RuntimeException{
    public NoSuchActionException(String message) {
        super(message);
    }

    public NoSuchActionException(StoryTree tree, String message) {
        super(
                tree.fileName != null ?
                        message + " in " + tree.fileName :
                        message
        );
    }
}
