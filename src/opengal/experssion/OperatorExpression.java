package opengal.experssion;

import opengal.core.IRuntime;
import opengal.nl.SerializeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public final class OperatorExpression implements Expression<Integer> {
    public static final int
            Plus = 0, Minus = 1,
            Multiple = 2, Divide = 3,
            Modulus = 4;
    private static final String[] opCodeMap = {
            "+",
            "-",
            "*",
            "/",
            "%"
    };
    public Expression<Integer> a, b;
    public int opCode;

    public OperatorExpression() {
    }

    public OperatorExpression(int opCode, Expression<Integer> a, Expression<Integer> b) {
        this.a = a;
        this.b = b;
        this.opCode = opCode;
    }

    @Override
    public @NotNull Integer calculate(@NotNull IRuntime runtime) {
        switch (opCode) {
            case 0:
                return a.calculate(runtime) + b.calculate(runtime); // +
            case 1:
                return a.calculate(runtime) - b.calculate(runtime); // -
            case 2:
                return a.calculate(runtime) * b.calculate(runtime); // *
            case 3:
                return a.calculate(runtime) / b.calculate(runtime); // /
            case 4:
                return a.calculate(runtime) % b.calculate(runtime); // %
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
