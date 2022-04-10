package opengal.tree;

import opengal.core.IRuntime;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Node {
    void serialize(@NotNull DataOutput output) throws IOException;

    void deserialize(@NotNull DataInput input) throws IOException;

    void operate(@NotNull IRuntime runtime);
}
