package opengal.excpetions;

import opengal.core.NodeTree;

public class InvariantException extends RuntimeException{
    public InvariantException(String message) {
        super(message);
    }
    public InvariantException(NodeTree tree, String message) {
        super(
                tree.fileName != null ?
                        message + " in " + tree.fileName :
                        message
        );
    }
}
