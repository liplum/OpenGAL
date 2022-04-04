package opengal.excpetions;

import opengal.core.NodeTree;

public class NoSuchValueException extends RuntimeException {
    public NoSuchValueException(String message) {
        super(message);
    }

    public NoSuchValueException(NodeTree tree, String message) {
        super(
                tree.fileName != null ?
                        message + " in " + tree.fileName :
                        message
        );
    }
}
