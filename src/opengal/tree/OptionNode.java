package opengal.tree;

import opengal.core.IInterpreter;

public class OptionNode implements StoryNode {
    public int optionCount;

    @Override
    public void operate(IInterpreter in) {
        in.setOptionCount(optionCount);
    }
}
