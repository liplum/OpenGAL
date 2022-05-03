package opengal.experssion;

import opengal.core.IExpressionReceiver;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public final class CompareExpression implements Expression<Boolean> {
    public static final int
            Equals = 0, NotEquals = 1,
            GreaterThan = 2, LessThan = 3,
            GreaterEquals = 4, LessEquals = 5;
    private static final String[] opCodeMap = {
            "==",
            "!=",
            ">",
            "<",
            ">=",
            "<="
    };
    public Expression<Object> a, b;
    public int opCode;

    public CompareExpression(int opCode, Expression<Object> a, Expression<Object> b) {
        this.a = a;
        this.b = b;
        this.opCode = opCode;
    }

    public CompareExpression() {
    }

    @Override
    public @NotNull Boolean calculate(@NotNull IExpressionReceiver runtime) {
        switch (opCode) {
            case Equals:
                return a.calculate(runtime) == b.calculate(runtime);// ==
            case NotEquals:
                return a.calculate(runtime) != b.calculate(runtime);// !=
            case GreaterThan: {
                Object i = a.calculate(runtime), j = b.calculate(runtime);
                if (i instanceof Integer && j instanceof Integer) {
                    return (Integer) i > (Integer) j;// >
                }
                throw new RuntimeException("compare the non-number variable!");
            }
            case LessThan: {
                Object i = a.calculate(runtime), j = b.calculate(runtime);
                if (i instanceof Integer && j instanceof Integer) {
                    return (Integer) i < (Integer) j;// <
                }
                throw new RuntimeException("compare the non-number variable!");
            }
            case GreaterEquals: {
                Object i = a.calculate(runtime), j = b.calculate(runtime);
                if (i instanceof Integer && j instanceof Integer) {
                    return (Integer) i >= (Integer) j;// >=
                }
                throw new RuntimeException("compare the non-number variable!");
            }
            case LessEquals: {
                Object i = a.calculate(runtime), j = b.calculate(runtime);
                if (i instanceof Integer && j instanceof Integer) {
                    return (Integer) i <= (Integer) j;// <=
                }
                throw new RuntimeException("compare the non-number variable!");
            }
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
