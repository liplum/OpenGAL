package opengal.experssion;

import opengal.core.IRuntime;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

public final class ConstantExpression<T> implements Expression<T> {
    public T value;

    public ConstantExpression(T value) {
        this.value = value;
    }

    public ConstantExpression() {
    }

    @Override
    public @NotNull T calculate(@NotNull IRuntime runtime) {
        return value;
    }

    @Override
    public @NotNull String toString() {
        return value.toString();
    }

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        SerializeUtils.writeThisID(output, this);
        SerializeUtils.serializeObj(output, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        value = (T) SerializeUtils.deserializeObj(input);
    }

    @NotNull
    @Override
    public Iterator<Expression<?>> iterator() {
        return Collections.emptyIterator();
    }
}
