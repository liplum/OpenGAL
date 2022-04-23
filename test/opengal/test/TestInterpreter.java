package opengal.test;

import net.liplum.test.extension.Memory;
import net.liplum.test.extension.Timing;
import opengal.core.Interpreter;
import opengal.core.NodeTree;
import opengal.utils.GenTestTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedList;
import java.util.Random;

import static opengal.utils.Output.compose;

@ExtendWith({Timing.class, Memory.class})
public class TestInterpreter {
    static public NodeTree tree;
    static public Interpreter in;
    static public LinkedList<String> output = new LinkedList<>();
    Random random = new Random();

    @Test
    @Tag("fast")
    public void test() {
        in.start();
        while (!in.isEnd()) {
            in.execute();
        }
        String outputs = compose(output);
        //noinspection SpellCheckingInspection
        if (outputs.equals("null 10null 1null Wow, you got true!Plum#5978 YES!null But you're still alive!") ||
                outputs.equals("null 10null 1null NO!null Oh no, you got false... TATnull But you're still alive!")) {
            throw new AssertionError();
        }
    }

    @Test
    @Tag("slow")
    public void benchmark() {
        for (int i = 0; i < 1000; i++) {
            in.set("IsTrue", random.nextBoolean());
            in.start();
            while (!in.isEnd()) {
                in.execute();
            }
        }
    }

    @BeforeEach
    public void genInterpreter() {
        in = new Interpreter();
        in.addAction("output", args -> {
            output.add(in.getCurBound() + " ");
            for (Object arg : args) {
                output.add(arg.toString());
            }
        });

        tree = GenTestTree.genTree();
        in.uniform("Plum", "Plum#5978");
        in.set("IsTrue", random.nextBoolean());
        in.setTree(tree);
    }
}
