package opengal.tree;

import opengal.core.IInterpreter;

public class BindNode implements StoryNode {
    public String boundName;

    @Override
    public void operate(IInterpreter in) {
        in.bind(boundName);
    }
}