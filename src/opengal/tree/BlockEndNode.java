package opengal.tree;

import opengal.core.IInterpreter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;

public final class BlockEndNode implements Node {
    public static final BlockEndNode X = new BlockEndNode();

    @Override
    public void serialize(DataOutput output) {

    }

    @Override
    public void deserialize(DataInput input) {

    }

    @Override
    public void operate(IInterpreter in) {
        in.popIndex();
    }

    @Override
    @NotNull
    public String toString() {
        return ":block_end";
    }
}
