package opengal.tree;

import opengal.core.IRuntime;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class BlockEntryNode implements Node {
    public int blockHead;

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        output.writeInt(blockHead);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        blockHead = input.readInt();
    }

    @Override
    public void operate(@NotNull IRuntime runtime) {
        runtime.pushIndex();
        runtime.jumpTo(blockHead + 1);
    }

    @Override
    @NotNull
    public String toString() {
        return ":entry " + blockHead;
    }
}
