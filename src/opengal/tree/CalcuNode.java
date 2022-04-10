package opengal.tree;

import opengal.core.IRuntime;
import opengal.core.OpenGAL;
import opengal.experssion.Expression;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class CalcuNode implements Node {
    public Expression<?> expr = OpenGAL.NothingExpr;

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        SerializeUtils.serializeExpr(output, expr);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        expr = SerializeUtils.deserializeExpr(input);
    }

    @Override
    public void operate(@NotNull IRuntime runtime) {
        expr.calculate(runtime);
    }

    @Override
    @NotNull
    public String toString() {
        return expr.toString();
    }
}
