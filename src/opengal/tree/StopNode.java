package opengal.tree;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;

public class StopNode implements Node{
    public static final StopNode X = new StopNode();
    @Override
    public String getIdentity() {
        return "Stop";
    }

    @Override
    public void serialize(DataOutput output) {

    }

    @Override
    public void deserialize(DataInput input) {

    }

    @Override
    public void operate(IInterpreter in) {
        in.terminate();
    }
}
