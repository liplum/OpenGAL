package opengal.tree;

import opengal.core.IInterpreter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;

public final class ReturnNode implements Node {
    public static final ReturnNode X = new ReturnNode();

    @Override
    public void serialize(DataOutput output) {

    }

    @Override
    public void deserialize(DataInput input) {

    }

    @Override
    public void operate(IInterpreter in) {
        in.rollbackPopIndex();
    }

    @Override
    @NotNull
    public String toString() {
        return ":return";
    }
}
