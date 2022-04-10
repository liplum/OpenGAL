package opengal.experssion;

import opengal.core.IRuntime;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public final class NorExpression implements Expression<Boolean> {
    public Expression<Boolean> exp;

    public NorExpression(Expression<Boolean> exp) {
        this.exp = exp;
    }

    public NorExpression() {
    }

    @Override
    public @NotNull Boolean calculate(@NotNull IRuntime runtime) {
        return !exp.calculate(runtime);
    }

    @Override
    public @NotNull String toString() {
        return "!" + exp;
    }

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        SerializeUtils.writeThisID(output, this);
        exp.serialize(output);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        exp.deserialize(input);
    }

    @NotNull
    @Override
    public Iterator<Expression<?>> iterator() {
        return SerializeUtils.singleIt(exp);
    }
}
