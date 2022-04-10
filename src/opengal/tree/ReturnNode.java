package opengal.tree;

import opengal.core.IRuntime;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;

public final class ReturnNode implements Node {
    public static final ReturnNode X = new ReturnNode();

    @Override
    public void serialize(@NotNull DataOutput output) {

    }

    @Override
    public void deserialize(@NotNull DataInput input) {

    }

    @Override
    public void operate(@NotNull IRuntime runtime) {
        runtime.rollbackPopIndex();
    }

    @Override
    @NotNull
    public String toString() {
        return ":return";
    }
}
