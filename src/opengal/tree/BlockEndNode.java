package opengal.tree;

import opengal.core.IRuntime;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;

public final class BlockEndNode implements Node {
    public static final BlockEndNode X = new BlockEndNode();

    @Override
    public void serialize(@NotNull DataOutput output) {

    }

    @Override
    public void deserialize(@NotNull DataInput input) {

    }

    @Override
    public void operate(@NotNull IRuntime runtime) {
        runtime.popIndex();
    }

    @Override
    @NotNull
    public String toString() {
        return ":block_end";
    }
}
