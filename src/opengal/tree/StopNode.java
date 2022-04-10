package opengal.tree;

import opengal.core.IRuntime;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;

public final class StopNode implements Node {
    public static final StopNode X = new StopNode();

    @Override
    public void serialize(@NotNull DataOutput output) {

    }

    @Override
    public void deserialize(@NotNull DataInput input) {

    }

    @Override
    public void operate(@NotNull IRuntime runtime) {
        runtime.terminate();
    }

    @Override
    @NotNull
    public String toString() {
        return ":stop";
    }
}
