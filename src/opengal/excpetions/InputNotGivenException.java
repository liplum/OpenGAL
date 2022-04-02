package opengal.excpetions;

import opengal.core.StoryTree;

public class InputNotGivenException extends RuntimeException {
    public InputNotGivenException() {
    }

    public InputNotGivenException(StoryTree tree) {
        super(tree.fileName != null ? tree.fileName : "");
    }
}
