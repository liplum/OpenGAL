package opengal.tree;

import opengal.core.IInterpreter;

public class TextNode implements StoryNode {
    public int textID;

    @Override
    public void operate(IInterpreter in) {
        in.setText(textID);
    }
}
