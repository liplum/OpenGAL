package opengal.exceptions;

import opengal.core.NodeTree;

public class InvariantException extends InterpretException  {
    public InvariantException(String message) {
        super(message);
    }

    public InvariantException(NodeTree tree, String message) {
        super(
                tree.file != null ?
                        message + " in " + tree.file :
                        message
        );
    }
}
