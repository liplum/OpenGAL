package opengal.tree;

import opengal.core.IInterpreter;

public class JumpNode implements StoryNode {
    public String destination;

    @Override
    public void operate(IInterpreter in) {
        in.jumpTo(destination);
    }
}
