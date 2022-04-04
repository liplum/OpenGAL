package opengal.tree;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BindNode implements Node{
    public String boundName;
    @Override
    public String getIdentity() {
        return "Bind";
    }

    @Override
    public void serialize(DataOutput output) throws IOException {
        output.writeUTF(boundName);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        boundName = input.readUTF();
    }

    @Override
    public void operate(IInterpreter in) {
        in.bind(boundName);
    }
}
