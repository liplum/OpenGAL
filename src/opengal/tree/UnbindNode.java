package opengal.tree;

import opengal.core.IInterpreter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;

public final class UnbindNode implements Node {
    public static final UnbindNode X = new UnbindNode();

    @Override
    public void serialize(DataOutput output) {

    }

    @Override
    public void deserialize(DataInput input) {

    }

    @Override
    public void operate(IInterpreter in) {
        in.unbind();
    }

    @Override
    @NotNull
    public String toString() {
        return ":unbind";
    }
}
