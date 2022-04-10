package opengal.experssion;

import opengal.core.IRuntime;
import opengal.core.OpenGAL;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

public class NothingExpression implements Expression<Object> {
    public static final NothingExpression X = new NothingExpression();

    @Override
    public @NotNull Object calculate(@NotNull IRuntime runtime) {
        return OpenGAL.Nothing;
    }

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        SerializeUtils.writeThisID(output, this);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {

    }

    @NotNull
    @Override
    public Iterator<Expression<?>> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public @NotNull String toString() {
        return "Nothing";
    }
}
