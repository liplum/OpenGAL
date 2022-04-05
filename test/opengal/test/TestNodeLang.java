package opengal.test;

import opengal.core.Interpreter;
import opengal.core.NodeTree;
import opengal.extension.Memory;
import opengal.extension.Timing;
import opengal.nl.NodeLang;
import opengal.utils.GenTestTree;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("CodeBlock2Expr")
@ExtendWith({Timing.class, Memory.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestNodeLang {
    static Random random = new Random();
    static boolean silent = false;
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
            if (!silent) {
                output.add(in.getCurBound() + " ");
                for (Object arg : args) {
                    output.add(arg.toString());
                }
            }
        });

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

        in.start();
        while (!in.isEnd()) {
            in.execute();
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
}
