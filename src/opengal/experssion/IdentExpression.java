package opengal.experssion;

import opengal.core.IRuntime;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

public final class IdentExpression<T> implements Expression<T> {
    public String key;

    public IdentExpression(String key) {
        this.key = key;
    }

    public IdentExpression() {
    }

    @Override
    public @NotNull T calculate(@NotNull IRuntime runtime) {
        return runtime.get(key);
    }

    @Override
    public @NotNull String toString() {
        return "@" + key;
    }

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        SerializeUtils.writeThisID(output, this);
        output.writeUTF(key);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        key = input.readUTF();
    }

    @NotNull
    @Override
    public Iterator<Expression<?>> iterator() {
        return Collections.emptyIterator();
    }
}
