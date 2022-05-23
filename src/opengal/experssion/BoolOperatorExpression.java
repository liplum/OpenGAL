package opengal.experssion;

import opengal.core.IExpressionReceiver;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public final class BoolOperatorExpression implements Expression<Boolean> {
    public static final int
            And = 0, Or = 1;
    private static final String[] opCodeMap = {
            "&&",
            "||"
    };
    public Expression<Boolean> a, b;
    public int opCode;

    public BoolOperatorExpression(int opCode, Expression<Boolean> a, Expression<Boolean> b) {
        this.opCode = opCode;
        this.a = a;
        this.b = b;
    }

    public BoolOperatorExpression() {
    }

    @Override
    public @NotNull Boolean calculate(@NotNull IExpressionReceiver memory) {
        Object i = a.calculate(memory);
        Object j = b.calculate(memory);
        boolean b1 = ExprUtils.isTrue(i);
        boolean b2 = ExprUtils.isTrue(j);

        switch (opCode) {
            case And:
                return b1 && b2; // &&
            case Or:
                return b1 || b2; // ||
            default:
                throw new RuntimeException("unknown opcode: " + opCode);
        }
    }

    public String getOperator() {
        return opCodeMap[opCode];
    }

    @Override
    public @NotNull String toString() {
        return a + " " + getOperator() + " " + b;
    }

    @Override
    public void serialize(@NotNull DataOutput output) throws IOException {
        SerializeUtils.writeThisID(output, this);
        output.writeByte(opCode);
        a.serialize(output);
        b.serialize(output);
    }

    @Override
    public void deserialize(@NotNull DataInput input) throws IOException {
        opCode = input.readByte();
        a = SerializeUtils.readByID(input.readByte());
        a.deserialize(input);
        b = SerializeUtils.readByID(input.readByte());
        b.deserialize(input);
    }

    @NotNull
    @Override
    public Iterator<Expression<?>> iterator() {
        return SerializeUtils.varargsIt(a, b);
    }
}
