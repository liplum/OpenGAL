package opengal.tree;

import opengal.core.IInterpreter;
import opengal.experssion.Expression;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ConditionNode implements Node {
    public Expression<Boolean> condition;
    public int trueDestination;
    public int falseDestination;

    @Override
    public void serialize(DataOutput output) throws IOException {
        SerializeUtils.serializeExpr(output, condition);
        output.writeInt(trueDestination);
        output.writeInt(falseDestination);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        condition = SerializeUtils.deserializeExpr(input);
        trueDestination = input.readInt();
        falseDestination = input.readInt();
    }

    @Override
    public void operate(IInterpreter in) {
        if (condition.calculate(in)) {
            in.jumpTo(trueDestination);
        } else {
            in.jumpTo(falseDestination);
        }
    }

    @Override
    @NotNull
    public String toString() {
        return ":if " + condition + " then " + trueDestination + " else " + falseDestination;
    }
}
