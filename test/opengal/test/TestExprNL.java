package opengal.test;

import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import opengal.syntax.ExpressionParser;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

public class TestExprNL {
    //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
    Expression<?> exprA = new ExpressionParser(List.of(
            "@au", "=", "(", "@c", "*", "(", "$u", "+", "43", ")", ")", "*", "30", "+", "6", ">=", "74"
    )).parse();
    //5+6*(7-2)
    Expression<?> exprB = new ExpressionParser(List.of(
            "5","+","6","*","(","7","-","2",")"
    )).parse();
    @Test
    public void testExprSerialize() throws IOException {
        OutputStream os = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        SerializeUtils.serializeExpr(dos,exprB);
    }
}
