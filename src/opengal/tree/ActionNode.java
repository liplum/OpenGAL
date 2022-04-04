package opengal.tree;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ActionNode implements Node {
    public String actionName;
    public Object[] args;

    @Override
    public void serialize(DataOutput output) throws IOException {
        output.writeUTF(actionName);
        output.writeInt(args.length);
        for (Object arg : args) {
            SerializeUtils.serializeObj(output, arg);
        }
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        actionName = input.readUTF();
        int argLen = input.readInt();
        args = new Object[argLen];
        for (int i = 0; i < argLen; i++) {
            args[i] = SerializeUtils.deserializeObj(input);
        }
    }

    @Override
    public void operate(IInterpreter in) {
        in.doAction(actionName, args);
    }
}
