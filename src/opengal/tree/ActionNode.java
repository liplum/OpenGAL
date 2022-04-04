package opengal.tree;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ActionNode implements Node {
    public static final int StringID = 0;
    public static final int BooleanID = 1;
    public static final int IntID = 2;
    public String actionName;
    public Object[] args;

    @Override
    public String getIdentity() {
        return "Action";
    }

    @Override
    public void serialize(DataOutput output) throws IOException {
        output.writeUTF(actionName);
        output.writeInt(args.length);
        for (Object arg : args) {
            if (arg instanceof String) {
                output.writeByte(StringID);
                output.writeUTF((String) arg);
            } else if (arg instanceof Integer) {
                output.writeByte(IntID);
                output.writeInt((int) arg);

            } else if (arg instanceof Boolean) {
                output.writeByte(BooleanID);
                output.writeBoolean((boolean) arg);
            }
        }
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        actionName = input.readUTF();
        int argLen = input.readInt();
        args = new Object[argLen];
        for (int i = 0; i < argLen; i++) {
            byte type = input.readByte();
            switch (type) {
                case StringID:
                    args[i] = input.readUTF();
                    break;
                case IntID:
                    args[i] = input.readInt();
                    break;
                case BooleanID:
                    args[i] = input.readBoolean();
                    break;
            }
        }
    }

    @Override
    public void operate(IInterpreter in) {
        in.doAction(actionName, args);
    }
}
