package opengal.test;

import net.liplum.test.extension.Memory;
import net.liplum.test.extension.Timing;
import opengal.core.IExpressionReceiver;
import opengal.experssion.ExprUtils;
import opengal.experssion.Expression;
import opengal.experssion.ExpressionParser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ExtendWith({Timing.class, Memory.class})
public class ExprTest {
    Expression<?> expr;
    //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
    List<String> tokens = Arrays.asList(
            "@au", "=", "(", "@c", "*", "(", "$u", "+", "43", ")", ")", "*", "30", "+", "6", ">=", "74"
    );

    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser(Arrays.asList("true", "&&", "!", "(", "\"1\"", "!=", "\"abc\"", ")"));
        Expression<?> exp = parser.parse();
        System.out.println(exp);
        Collection<String> tokens = ExprUtils.splitTokens("@SSS != \"ebw bad guy\" + 10");
        System.out.println(tokens);
        Collection<String> tokens2 = ExprUtils.splitTokens("1 != abc");
        System.out.println(tokens2);
        ExpressionParser p1 = new ExpressionParser(tokens);
        System.out.println(p1.parse());
    }

    static class FakeMemory implements IExpressionReceiver {


        @Override
        public void set(@NotNull String name, @NotNull Object value) {
            throw new RuntimeException();
        }

        @Override
        public <T> @NotNull T get(@NotNull String name) {
            throw new RuntimeException();
        }
    }

    @Test
    @Tag("fast")
    public void testExpr() {
        //@au = (@c * ($u + 43 )) * 30 + 6 >= 74
        ExpressionParser parser = new ExpressionParser(tokens);
        expr = parser.parse();
        String exprStr = expr.toString();
        assert exprStr.equals("@au = (@c * (\"$u\" + 43)) * 30 + 6 >= 74");
        Object testNorInt = ExpressionParser.parseBy("!1").calculate(new FakeMemory());//false
        Object testIntNotEqualsString = ExpressionParser.parseBy("1 != abc").calculate(new FakeMemory());
        assert !((boolean) testNorInt);
        assert (boolean) testIntNotEqualsString;
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
