package opengal.tree;

import opengal.core.IInterpreter;

public class ActionNode implements StoryNode {
    public String actionName;
    public Object[] args;

    @Override
    public void operate(IInterpreter in) {
        in.doAction(actionName, args);
    }
}
