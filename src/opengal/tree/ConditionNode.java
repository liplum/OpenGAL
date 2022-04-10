package opengal.tree;

import opengal.core.IRuntime;
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
    public void serialize(@NotNull DataOutput output) throws IOException {
        SerializeUtils.serializeExpr(output, condition);
        output.writeInt(trueDestination);
        output.writeInt(falseDestination);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        condition = SerializeUtils.deserializeExpr(input);
        trueDestination = input.readInt();
        falseDestination = input.readInt();
    }

    @Override
    public void operate(@NotNull IRuntime runtime) {
        if (condition.calculate(runtime))
            runtime.jumpTo(trueDestination);
        else
            runtime.jumpTo(falseDestination);
    }

    @Override
    @NotNull
    public String toString() {
        return ":if " + condition + " then " + trueDestination + " else " + falseDestination;
    }
}
