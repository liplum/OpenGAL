package opengal.excpetions;

import opengal.core.NodeTree;

public class NoSuchActionException extends RuntimeException {
    public NoSuchActionException(String message) {
        super(message);
    }

    public NoSuchActionException(NodeTree tree, String message) {
        super(
                tree.fileName != null ?
                        message + " in " + tree.fileName :
                        message
        );
    }
}
