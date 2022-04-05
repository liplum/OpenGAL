package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.core.OpenGAL;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

public class NothingExpression implements Expression<Object> {
    @Override
    public Object calculate(IInterpreter interpreter) {
        return OpenGAL.Nothing;
    }

    @Override
    public void serialize(DataOutput output) throws IOException {
        SerializeUtils.writeThisID(output,this);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {

    }

    @NotNull
    @Override
    public Iterator<Expression<?>> iterator() {
        return Collections.emptyIterator();
    }
}
