package opengal.tree;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;

public class BlockEndNode implements Node {
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
    public String toString() {
        return ":end";
    }
}
