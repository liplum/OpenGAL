package opengal.tree;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class JumpNode implements Node {
    public int destination;
    @Override
    public String getIdentity() {
        return "Jump";
    }

    @Override
    public void serialize(DataOutput output) throws IOException {
        output.writeInt(destination);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        destination = input.readInt();
    }

    @Override
    public void operate(IInterpreter in) {
        in.jumpTo(destination);
    }
}
