package opengal.test;

import net.liplum.test.extension.Memory;
import net.liplum.test.extension.Timing;
import opengal.experssion.Expression;
import opengal.experssion.ExpressionParser;
import opengal.nl.SerializeUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.*;
import java.util.Arrays;

@ExtendWith({Timing.class, Memory.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestExprNL {
    //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
    public static Expression<?> exprA = new ExpressionParser(Arrays.asList(
            "@au", "=", "(", "@c", "*", "(", "$u", "+", "43", ")", ")", "*", "30", "+", "6", ">=", "74"
    )).parse();
    //5+6*(7-2)
    public static Expression<?> exprB = new ExpressionParser(Arrays.asList(
            "5", "+", "6", "*", "(", "7", "-", "2", ")"
    )).parse();
    public static Expression<?> original = exprA;
    public static byte[] bytes;

    @Test
    @Order(0)
    @Tag("fast")
    public void testExprSerialize() throws IOException {
        try (
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(os)
        ) {
            SerializeUtils.serializeExpr(dos, original);
            dos.flush();
            bytes = os.toByteArray();
        }
    }

    @Test
    @Order(1)
    @Tag("fast")
    public void testExprDeserialize() throws IOException {
        try (
                ByteArrayInputStream is = new ByteArrayInputStream(bytes);
                DataInputStream dis = new DataInputStream(is)
        ) {
            Expression<?> expr = SerializeUtils.deserializeExpr(dis);
            assert expr.toString().equals("@au = (@c * ($u + 43)) * 30 + 6 >= 74");
        }
    }
}
