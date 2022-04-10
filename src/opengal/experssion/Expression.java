package opengal.experssion;

import opengal.core.IRuntime;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Expression<Type> extends Iterable<Expression<?>> {
    @NotNull
    Type calculate(@NotNull IRuntime runtime);

    @NotNull
    String toString();

    void serialize(@NotNull DataOutput output) throws IOException;

    void deserialize(@NotNull DataInput input) throws IOException;
}
