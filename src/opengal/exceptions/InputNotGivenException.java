package opengal.exceptions;

import opengal.core.NodeTree;

public class InputNotGivenException extends InterpretException {
    public InputNotGivenException() {
    }

    public InputNotGivenException(NodeTree tree) {
        super(tree.file != null ? tree.file : "");
    }
}
