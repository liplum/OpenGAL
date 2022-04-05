package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public final class AssignExpression<T> implements Expression<T> {
    public Expression<T> exp;
    public IdentExpression<T> ident;

    public AssignExpression() {
    }

    public AssignExpression(IdentExpression<T> left,Expression<T> right) {
        this.exp = right;
        this.ident = left;
    }

    @Override
    public T calculate(IInterpreter interpreter) {
        T result = exp.calculate(interpreter);
        interpreter.set(ident.key, result);
        return result;
    }

    @Override
    public String toString() {
        return ident + " = " + exp;
    }

    @Override
    public void serialize(DataOutput output) throws IOException {
        SerializeUtils.writeThisID(output, this);
        ident.serialize(output);
        exp.serialize(output);
    }

    @Override
    public void deserialize(DataInput input) throws IOException {
        ident = SerializeUtils.readByID(input.readByte());
        ident.deserialize(input);
        exp = SerializeUtils.readByID(input.readByte());
        exp.deserialize(input);
    }

    @NotNull
    @Override
    public Iterator<Expression<?>> iterator() {
        return SerializeUtils.varargsIt(exp, ident);
    }
}
