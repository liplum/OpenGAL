package opengal.tree;

import com.sun.istack.internal.Nullable;
import opengal.core.IInterpreter;

public class ConditionNode implements StoryNode {
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
