package opengal.exceptions;

import opengal.core.NodeTree;

public class NoSuchActionException extends InterpretException {
    public NoSuchActionException(String message) {
        super(message);
    }

    public NoSuchActionException(NodeTree tree, String message) {
        super(
                tree.file != null ?
                        message + " in " + tree.file :
                        message
        );
    }
}
