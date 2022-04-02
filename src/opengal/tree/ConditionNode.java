package opengal.tree;

import opengal.core.IInterpreter;

public class ConditionNode implements Node {
    public String conditionName;
    public int trueDestination;
    public int falseDestination;

    @Override
    public void operate(IInterpreter in) {
        boolean bool = in.getBool(conditionName);
        if (bool) {
            in.jumpTo(trueDestination);
        } else {
            in.jumpTo(falseDestination);
        }
    }
}
