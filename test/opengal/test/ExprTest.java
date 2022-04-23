package opengal.test;

import net.liplum.test.extension.Memory;
import net.liplum.test.extension.Timing;
import opengal.experssion.Expression;
import opengal.experssion.ExpressionParser;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

@ExtendWith({Timing.class, Memory.class})
public class ExprTest {
    Expression<?> expr;
    //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
    List<String> tokens = Arrays.asList(
            "@au", "=", "(", "@c", "*", "(", "$ u", "+", "43", ")", ")", "*", "30", "+", "6", ">=", "74"
    );

    @Test
    @Tag("fast")
    public void testExpr() {
        //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
        ExpressionParser parser = new ExpressionParser(tokens);
        expr = parser.parse();
        assert expr.toString().equals("@au = (@c * ($ u + 43)) * 30 + 6 >= 74");
    }

    @Test
    @Tag("slow")
    public void benchmark() {
        for (int i = 0; i < 1000; i++) {
            ExpressionParser parser = new ExpressionParser(tokens);
            expr = parser.parse();
        }
    }

}
