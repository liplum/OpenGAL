package opengal.test;

import opengal.syntax.Expression;
import opengal.syntax.ExpressionParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith({Timing.class, Memory.class})
public class ExprTest {
    Expression<?> expr;
    //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
    List<String> tokens = List.of(
            "@au", "=", "(", "@c", "*", "(", "$u", "+", "43", ")", ")", "*", "30", "+", "6", ">=", "74"
    );

    @Test
    @Disabled
    public void testExpr() {
        //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
        ExpressionParser parser = new ExpressionParser(tokens);
        expr = parser.parse();
    }

    @Test
    public void benchmark() {
        for (int i = 0; i < 1000000; i++) {
            ExpressionParser parser = new ExpressionParser(tokens);
            expr = parser.parse();
        }
    }

}
