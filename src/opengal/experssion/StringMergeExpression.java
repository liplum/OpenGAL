package opengal.experssion;

import opengal.core.IExpressionReceiver;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public final class StringMergeExpression implements Expression<String> {
    public Expression<Object> a, b;

    public StringMergeExpression(Expression<Object> a, Expression<Object> b) {
        this.a = a;
        this.b = b;
    }

    public StringMergeExpression() {
    }

    @Override
    public @NotNull String calculate(@NotNull IExpressionReceiver runtime) {
        String str = String.valueOf(a.calculate(runtime));
        return str + b.calculate(runtime);
    }

    @Override
    public @NotNull String toString() {
        return a + ".." + b;
    }

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        SerializeUtils.writeThisID(output, this);
        a.serialize(output);
        b.serialize(output);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        a = SerializeUtils.readByID(input.readByte());
        a.deserialize(input);
        b = SerializeUtils.readByID(input.readByte());
        b.deserialize(input);
    }

    @NotNull
    @Override
    public Iterator<Expression<?>> iterator() {
        return SerializeUtils.varargsIt(a, b);
    }
}
