package opengal.tree;

import opengal.core.IRuntime;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class JumpNode implements Node {
    public int destination;

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        output.writeInt(destination);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        destination = input.readInt();
    }

    @Override
    public void operate(@NotNull IRuntime runtime) {
        runtime.jumpTo(destination);
    }

    @Override
    @NotNull
    public String toString() {
        return ":jump " + destination;
    }
}
