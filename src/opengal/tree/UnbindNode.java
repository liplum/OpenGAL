package opengal.tree;

import opengal.core.IInterpreter;

public class UnbindNode implements StoryNode {
    @Override
    public void operate(IInterpreter in) {
        in.unbind();
    }
}
