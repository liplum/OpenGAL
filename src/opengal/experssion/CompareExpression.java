package opengal.experssion;

import opengal.core.IRuntime;
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
    public @NotNull Boolean calculate(@NotNull IRuntime runtime) {
        switch (opCode) {
            case 0:
                return a.calculate(runtime) == b.calculate(runtime);// ==
            case 1:
                return a.calculate(runtime) != b.calculate(runtime);// !=
            case 2: {
                Object i = a.calculate(runtime), j = a.calculate(runtime);
                if (i instanceof Integer && j instanceof Integer) {
                    return (Integer) i > (Integer) j;// >
                }
                throw new RuntimeException("compare the non-number variable!");
            }
            case 3: {
                Object i = a.calculate(runtime), j = a.calculate(runtime);
                if (i instanceof Integer && j instanceof Integer) {
                    return (Integer) i < (Integer) j;// <
                }
                throw new RuntimeException("compare the non-number variable!");
            }
            case 4: {
                Object i = a.calculate(runtime), j = a.calculate(runtime);
                if (i instanceof Integer && j instanceof Integer) {
                    return (Integer) i >= (Integer) j;// >=
                }
                throw new RuntimeException("compare the non-number variable!");
            }
            case 5: {
                Object i = a.calculate(runtime), j = a.calculate(runtime);
                if (i instanceof Integer && j instanceof Integer) {
                    return (Integer) i <= (Integer) j;// <=
                }
                throw new RuntimeException("compare the non-number variable!");
            }
            default:
                throw new RuntimeException("unknow opcode: " + opCode);
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
