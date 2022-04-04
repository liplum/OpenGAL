package opengal.tree;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ConditionNode implements Node {
    public String conditionName;
    public int trueDestination;
    public int falseDestination;

    @Override
    public void serialize(DataOutput output) throws IOException {
        output.writeUTF(conditionName);
        output.writeInt(trueDestination);
        output.writeInt(falseDestination);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        conditionName = input.readUTF();
        trueDestination = input.readInt();
        falseDestination = input.readInt();
    }

    @Override
    public void operate(IInterpreter in) {
        boolean bool = in.getBool(conditionName);
        if (bool) {
            in.jumpTo(trueDestination);
        } else {
            in.jumpTo(falseDestination);
        }
    }
}
