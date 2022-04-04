package opengal.nl;

import opengal.excpetions.UnmappedObjectException;
import opengal.syntax.Expression;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

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

    public static void serializeExpr(DataOutput output, Expression<?> expr){

    }

    public static <T> Expression<T> serializeExpr(DataInput input){
        return null;
    }
}
