package opengal.test;

import opengal.extension.Memory;
import opengal.extension.Timing;
import opengal.syntax.Expression;
import opengal.syntax.ExpressionParser;
import opengal.utils.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith({Timing.class, Memory.class})
public class ExprTest {
    Expression<?> expr;
    //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
    List<String> tokens = Lists.of(
            "@au", "=", "(", "@c", "*", "(", "$ u", "+", "43", ")", ")", "*", "30", "+", "6", ">=", "74"
    );

    @Test
    @Tag("fast")
    public void testExpr() {
        //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
        ExpressionParser parser = new ExpressionParser(tokens);
        expr = parser.parse();
        System.out.println(expr);
        System.out.flush();
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
