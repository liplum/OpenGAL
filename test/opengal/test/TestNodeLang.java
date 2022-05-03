package opengal.test;

import net.liplum.test.extension.Memory;
import net.liplum.test.extension.Timing;
import opengal.core.Interpreter;
import opengal.core.NodeTree;
import opengal.nl.NodeLang;
import opengal.utils.GenTestTree;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import static opengal.utils.Output.compose;

@ExtendWith({Timing.class, Memory.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestNodeLang {
    static Random random = new Random();
    static LinkedList<String> output = new LinkedList<>();
    static byte[] file;

    @Test
    @Order(0)
    @Tag("fast")
    public void testSerialize() throws IOException {
        NodeTree tree = GenTestTree.genTree();

        NodeLang nl = NodeLang.Default;

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            nl.serializeTo(tree, os);
            file = os.toByteArray();
        }
    }

    @Test
    @Order(1)
    @Tag("fast")
    public void testDeserialize() throws IOException {
        NodeLang nl = NodeLang.Default;
        NodeTree tree;
        try (
                ByteArrayInputStream is = new ByteArrayInputStream(file)
        ) {
            tree = nl.deserializeFrom(is);
        }
        Interpreter in = new Interpreter();
        in.addAction("output", args -> {
            output.add(in.getCurBound() + " ");
            for (Object arg : args) {
                output.add(arg.toString());
            }
        });

        in.uniform("Plum", "Plum#5978");
        in.set("IsTrue", random.nextBoolean());
        in.setTree(tree);

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
}
