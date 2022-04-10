package opengal.test;

import opengal.experssion.ExprUtils;
import opengal.extension.Memory;
import opengal.extension.Timing;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collection;

import static opengal.utils.Output.compose;

@ExtendWith({Timing.class, Memory.class})
public class TestExprTokenSplit {
    @Test
    @Tag("fast")
    public void testSplit() {
        String str1 = "@res=(5+7) +@a == 10 + @5";
        String str2 = "@au=(@c *($u +  43)) * 30 + 6 >= 74";
        Collection<String> tokens1 = ExprUtils.splitTokens(str1);
        assert compose(tokens1).equals("@res = ( 5 + 7 ) + @a == 10 + @5");
        Collection<String> tokens2 = ExprUtils.splitTokens(str2);
        assert compose(tokens2).equals("@au = ( @c * ( $u + 43 ) ) * 30 + 6 >= 74");
    }
}
