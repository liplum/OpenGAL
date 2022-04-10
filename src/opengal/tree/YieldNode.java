package opengal.tree;

import opengal.core.IRuntime;
import opengal.core.OpenGAL;
import opengal.experssion.Expression;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class YieldNode implements Node {
    public Expression<?> expr = OpenGAL.NothingExpr;

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        boolean hasExpr = expr != OpenGAL.NothingExpr;
        output.writeBoolean(hasExpr);
        if (hasExpr) {
            SerializeUtils.serializeExpr(output, expr);
        }
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        boolean hasExpr = input.readBoolean();
        if (hasExpr) {
            expr = SerializeUtils.deserializeExpr(input);
        }
    }

    @Override
    public void operate(@NotNull IRuntime runtime) {
        runtime.set("__yield__", expr.calculate(runtime));
        runtime.blockExecution();
    }

    @Override
    @NotNull
    public String toString() {
        return ":yield " + expr;
    }
}
