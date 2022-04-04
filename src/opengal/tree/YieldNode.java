package opengal.tree;

import opengal.core.IInterpreter;
import opengal.core.OpenGAL;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class YieldNode implements Node {
    @Nullable
    public Object value;

    @Override
    public void serialize(DataOutput output) throws IOException {
        SerializeUtils.serializeObj(output, value);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        value = SerializeUtils.deserializeObj(input);
    }

    @Override
    public void operate(IInterpreter in) {
        in.set("__yield__", value != null ? value : OpenGAL.Nothing);
        in.blockExecute();
    }
}
