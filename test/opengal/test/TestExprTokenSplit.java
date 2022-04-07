package opengal.test;

import opengal.experssion.ExprUtils;
import opengal.extension.Memory;
import opengal.extension.Timing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collection;
import java.util.LinkedList;

@ExtendWith({Timing.class, Memory.class})
public class TestExprTokenSplit {
    static public LinkedList<String> output = new LinkedList<>();

    public static void printAll(Collection<?> objects) {
        for (Object obj : objects) {
            output.add(obj.toString());
            output.add(" ");
        }
        output.add("\n");
    }

    @Test
    @Tag("fast")
    public void testSplit() {
        String str1 = "@res=(5+7) +@a == 10 + @5";
        String str2 = "@au=(@c *($u +  43)) * 30 + 6 >= 74";
        Collection<String> tokens1 = ExprUtils.splitTokens(str1);
        output.add("Tokens 1 -- " + str1 + "\n");
        printAll(tokens1);
        output.add("---------------------------\n");
        Collection<String> tokens2 = ExprUtils.splitTokens(str2);
        output.add("Tokens 2 -- " + str2 + "\n");
        printAll(tokens2);
    }

    @AfterEach
    public void printOutput() {
        System.out.println("Output:");
        for (String line : output) {
            System.out.print(line);
        }
        System.out.flush();
    }

}
