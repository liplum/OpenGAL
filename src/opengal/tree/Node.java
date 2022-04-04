package opengal.tree;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Node {
    void serialize(DataOutput output) throws IOException;

    void deserialize(DataInput input) throws IOException;

    void operate(IInterpreter in);
}
