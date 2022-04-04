package opengal.tree;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BlockEntryNode implements Node {
    public int blockHead;
    @Override
    public String getIdentity() {
        return "BlockEntry";
    }

    @Override
    public void serialize(DataOutput output) throws IOException {
        output.writeInt(blockHead);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        blockHead = input.readInt();
    }

    @Override
    public void operate(IInterpreter in) {
        in.pushIndex();
        in.jumpTo(blockHead + 1);
    }
}
