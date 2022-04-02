package opengal.tree;

import opengal.core.IInterpreter;
import org.jetbrains.annotations.Nullable;

public class ConditionNode implements Node{
    public String conditionName;
    @Nullable
    public String trueDestination;
    @Nullable
    public String falseDestination;

    @Override
    public void operate(IInterpreter in) {
        boolean bool = in.getBool(conditionName);
        if (bool && trueDestination != null) {
            in.jumpTo(trueDestination);
        } else if (falseDestination != null) {
            in.jumpTo(falseDestination);
        }
    }
}
