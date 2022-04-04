package opengal.excpetions;

import opengal.core.NodeTree;

public class InputNotGivenException extends RuntimeException {
    public InputNotGivenException() {
    }

    public InputNotGivenException(NodeTree tree) {
        super(tree.fileName != null ? tree.fileName : "");
    }
}
