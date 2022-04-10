package opengal.exceptions;

import opengal.core.NodeTree;

public class NoSuchValueException extends InterpretException {
    public NoSuchValueException(String message) {
        super(message);
    }

    public NoSuchValueException(NodeTree tree, String message) {
        super(
                tree.file != null ?
                        message + " in " + tree.file :
                        message
        );
    }
}
