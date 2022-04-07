package opengal.tree;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ActionNode implements Node {
    public static Object[] emptyArgs = new Object[0];
    public String actionName;
    public Object[] args = emptyArgs;

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

    @Override
    @NotNull
    public String toString() {
        if (args.length > 0) {
            return ":action " + actionName + argsToString();
        } else {
            return ":action " + actionName;
        }
    }

    @NotNull
    public String argsToString() {
        if (args == null)
            return "null";

        int iMax = args.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        b.append('(');
        for (int i = 0; ; i++) {
            Object arg = args[i];
            boolean isString = arg instanceof String;
            if (isString)
                b.append('\"');
            b.append(arg);
            if (isString)
                b.append('\"');
            if (i == iMax)
                return b.append(')').toString();
            b.append(", ");
        }
    }
}
