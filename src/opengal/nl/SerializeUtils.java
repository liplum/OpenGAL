package opengal.nl;

import opengal.excpetions.UnmappedObjectException;
import opengal.syntax.Expression;
import opengal.syntax.experssion.*;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

public class SerializeUtils {
    public static final int NothingID = -1;
    public static final int StringID = 0;
    public static final int BooleanID = 1;
    public static final int IntID = 2;

    public static void serializeObj(DataOutput output, @Nullable Object obj) throws IOException {
        if (obj == null) {
            output.writeByte(NothingID);
        } else if (obj instanceof String) {
            output.writeByte(StringID);
            output.writeUTF((String) obj);
        } else if (obj instanceof Integer) {
            output.writeByte(IntID);
            output.writeInt((int) obj);

        } else if (obj instanceof Boolean) {
            output.writeByte(BooleanID);
            output.writeBoolean((boolean) obj);
        }
    }

    @Nullable
    public static Object deserializeObj(DataInput input) throws IOException {
        byte type = input.readByte();
        switch (type) {
            case NothingID:
                return null;
            case StringID:
                return input.readUTF();
            case IntID:
                return input.readInt();
            case BooleanID:
                return input.readBoolean();
        }
        throw new UnmappedObjectException("Can't map type code " + type);
    }

    public static final byte
            NothingExpr = -1,
            AssignExpr = 0,
            BoolOpExpr = 1,
            IdentExpr = 2,
            CompareExpr = 3,
            NorExpr = 4,
            StringMergeExpr = 5,
            ParExpr = 6,
            OpExpr = 7,
            ConstantExpr = 8;
    @SuppressWarnings("rawtypes")
    public static final Map<Class<? extends Expression>, Byte> clz2Code;
    public static final Map<Byte, Supplier<Expression<?>>> code2Expr;

    static {
        clz2Code = new HashMap<>();
        clz2Code.put(AssignExpression.class, AssignExpr);
        clz2Code.put(BoolOperatorExpression.class, BoolOpExpr);
        clz2Code.put(IdentExpression.class, IdentExpr);
        clz2Code.put(CompareExpression.class, CompareExpr);
        clz2Code.put(NorExpression.class, NorExpr);
        clz2Code.put(StringMergeExpression.class, StringMergeExpr);
        clz2Code.put(ParExpression.class, ParExpr);
        clz2Code.put(OperatorExpression.class, OpExpr);
        clz2Code.put(ConstantExpression.class, ConstantExpr);
        clz2Code.put(NothingExpression.class, NothingExpr);

        code2Expr = new HashMap<>();
        code2Expr.put(AssignExpr, AssignExpression::new);
        code2Expr.put(BoolOpExpr, BoolOperatorExpression::new);
        code2Expr.put(IdentExpr, IdentExpression::new);
        code2Expr.put(CompareExpr, CompareExpression::new);
        code2Expr.put(NorExpr, NorExpression::new);
        code2Expr.put(StringMergeExpr, StringMergeExpression::new);
        code2Expr.put(ParExpr, ParExpression::new);
        code2Expr.put(OpExpr, OperatorExpression::new);
        code2Expr.put(ConstantExpr, ConstantExpression::new);
        code2Expr.put(NothingExpr, () -> NothingExpression.X);
    }

    /**
     * Serializes as a prefix expression.
     */
    public static void serializeExpr(DataOutput output, Expression<?> exprTree) throws IOException {
        exprTree.serialize(output);
    }

    /**
     * Deserializes a prefix expression.
     */
    @SuppressWarnings("unchecked")
    public static <T> Expression<T> deserializeExpr(DataInput input) throws IOException {
        Expression<?> rootExpr = SerializeUtils.readByID(input.readByte());
        rootExpr.deserialize(input);
        return (Expression<T>) rootExpr;
    }


    @SuppressWarnings("rawtypes")
    public static void writeThisID(DataOutput output, Class<? extends Expression> exprClz) throws IOException {
        Byte code = clz2Code.get(exprClz);
        if (code == null) {
            throw new UnmappedObjectException(exprClz.getName());
        }
        output.writeByte(code);
    }

    public static void writeThisID(DataOutput output, Expression<?> expr) throws IOException {
        writeThisID(output, expr.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Expression<?>> T readByID(byte ID) {
        Supplier<Expression<?>> exprGen = code2Expr.get(ID);
        if (exprGen == null) {
            throw new UnmappedObjectException("Can't map expression code " + ID);
        }
        return (T) exprGen.get();
    }

    public static void preIt(LinkedList<Expression<?>> res, Expression<?> expr) {
        res.add(expr);
        for (Expression<?> sub : expr) {
            preIt(res, sub);
        }
    }

    public static LinkedList<Expression<?>> preIt(Expression<?> expr) {
        LinkedList<Expression<?>> res = new LinkedList<>();
        preIt(res, expr);
        return res;
    }

    public static <T> Iterator<T> singleIt(T e) {
        return new Iterator<T>() {
            boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public T next() {
                hasNext = false;
                return e;
            }
        };
    }

    @SafeVarargs
    public static <T> Iterator<T> varargsIt(T... es) {
        return Arrays.stream(es).iterator();
    }
}
