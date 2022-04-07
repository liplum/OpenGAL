package opengal.tree;

import opengal.core.IInterpreter;
import opengal.experssion.Expression;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class CalcuNode implements Node {
    public Expression<?> expr;

    @Override
    public void serialize(DataOutput output) throws IOException {
        SerializeUtils.serializeExpr(output, expr);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        expr = SerializeUtils.deserializeExpr(input);
    }

    @Override
    public void operate(IInterpreter in) {
        expr.calculate(in);
    }

    @Override
    @NotNull
    public String toString() {
        return expr.toString();
    }
}
