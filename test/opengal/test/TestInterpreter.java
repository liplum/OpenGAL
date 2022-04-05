package opengal.test;

import opengal.core.Interpreter;
import opengal.core.NodeTree;
import opengal.extension.Memory;
import opengal.extension.Timing;
import opengal.utils.GenTestTree;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("CodeBlock2Expr")
@ExtendWith({Timing.class, Memory.class})
public class TestInterpreter {
    static public NodeTree tree;
    static public Interpreter in;
    static public LinkedList<String> output = new LinkedList<>();
    public boolean silent = true;
    Random random = new Random();

    @Test
    @Tag("fast")
    public void test() {
        in.start();
        while (!in.isEnd()) {
            in.execute();
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

    @AfterEach
    public void printOutput() {
        if (!silent) {
            System.out.println("Output:");
            for (String line : output) {
                System.out.print(line);
            }
            System.out.flush();
        }
    }

    @BeforeEach
    public void genInterpreter() {
        in = new Interpreter();
        in.addAction("output", args -> {
            if (!silent) {
                output.add(in.getCurBound() + " ");
                for (Object arg : args) {
                    output.add(arg.toString());
                }
            }
        });

        tree = GenTestTree.genTree();
        in.uniform("Plum", "Plum#5978");
        in.set("IsTrue", random.nextBoolean());
        if (!silent) {
            in.beforeExecute(() -> {
                output.add("[" + in.getCurIndex() + "]");
            });
            in.afterExecute(() -> {
                output.add("\n");
            });
        }
        in.setTree(tree);
    }
}
