package opengal.experssion;

import opengal.core.IExpressionReceiver;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public @NotNull Boolean calculate(@NotNull IExpressionReceiver memory) {
        switch (opCode) {
            case Equals:
                return Objects.equals(a.calculate(memory), (b.calculate(memory)));// == , use Object.equals instead of ==
            case NotEquals:
                return !Objects.equals(a.calculate(memory), (b.calculate(memory)));// !=, use !Object.equals instead of !=
            case GreaterThan: {
                Object i = a.calculate(memory), j = b.calculate(memory);
                if (i instanceof Comparable && j instanceof Comparable) {
                    return ((Comparable) i).compareTo(j) > 0;
                }
                throw new RuntimeException("Can't compare " + i + " > " + j);
            }
            case LessThan: {
                Object i = a.calculate(memory), j = b.calculate(memory);
                if (i instanceof Comparable && j instanceof Comparable) {
                    return ((Comparable) i).compareTo(j) < 0;
                }
                throw new RuntimeException("Can't compare " + i + " < " + j);
            }
            case GreaterEquals: {
                Object i = a.calculate(memory), j = b.calculate(memory);
                if (Objects.equals(i, j)) return true;
                if (i instanceof Comparable && j instanceof Comparable) {
                    return ((Comparable) i).compareTo(j) >= 0;
                }
                throw new RuntimeException("Can't compare " + i + " >= " + j);
            }
            case LessEquals: {
                Object i = a.calculate(memory), j = b.calculate(memory);
                if (Objects.equals(i, j)) return true;
                if (i instanceof Comparable && j instanceof Comparable) {
                    return ((Comparable) i).compareTo(j) <= 0;
                }
                throw new RuntimeException("Can't compare " + i + " <= " + j);
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
