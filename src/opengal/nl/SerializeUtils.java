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

    public static final int
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
    public static final Map<Class<? extends Expression>, Integer> clz2Code;
    public static final Map<Byte, Supplier<Expression<?>>> code2Expr;

    static {
        clz2Code = new HashMap<>();
        clz2Code.put(AssignExpression.class, 0);
        clz2Code.put(BoolOperatorExpression.class, 1);
        clz2Code.put(IdentExpression.class, 2);
        clz2Code.put(CompareExpression.class, 3);
        clz2Code.put(NorExpression.class, 4);
        clz2Code.put(StringMergeExpression.class, 5);
        clz2Code.put(ParExpression.class, 6);
        clz2Code.put(OperatorExpression.class, 7);
        clz2Code.put(ConstantExpression.class, 8);

        code2Expr = new HashMap<>();
        code2Expr.put((byte) 0,AssignExpression::new);
        code2Expr.put((byte) 1,BoolOperatorExpression::new);
        code2Expr.put((byte) 2,IdentExpression::new);
        code2Expr.put((byte) 3,CompareExpression::new);
        code2Expr.put((byte) 4,NorExpression::new);
        code2Expr.put((byte) 5,StringMergeExpression::new);
        code2Expr.put((byte) 6,ParExpression::new);
        code2Expr.put((byte) 7,OperatorExpression::new);
        code2Expr.put((byte) 8,ConstantExpression::new);
    }

    /**
     * Serializes as a prefix expression.
     */
    public static void serializeExpr(DataOutput output, Expression<?> exprTree) throws IOException {
        for (Expression<?> expr : preIt(exprTree)) {
            Integer code = clz2Code.get(expr.getClass());
            if(code == null){
                throw new UnmappedObjectException(expr.getClass().getName());
            }
            output.writeByte(code);
            expr.serialize(output);
        }
    }

    /**
     * Deserializes a prefix expression.
     */
    public static <T> Expression<T> serializeExpr(DataInput input) throws IOException {
        byte code = input.readByte();
        Supplier<Expression<?>> exprGen = code2Expr.get(code);
        if(exprGen == null){
            throw new UnmappedObjectException("Can't map expression code " + code);
        }
        Expression<?> expr = exprGen.get();
        return null;
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
