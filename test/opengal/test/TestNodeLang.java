package opengal.test;

import opengal.core.Interpreter;
import opengal.core.NodeTree;
import opengal.nl.NodeLang;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

@ExtendWith(Timing.class)
public class TestNodeLang {
    Random random = new Random();
    public boolean silent = false;
    public LinkedList<String> output = new LinkedList<>();

    @Test
    public void testSerialize() throws IOException {
        NodeTree tree = GenTestTree.genTree();

        NodeLang nl = NodeLang.Default;
        String temp = System.getenv("TEMP");
        File file = new File(temp + "/nl_serialized.node");
        System.out.println(file.getName());
        if (!file.exists()) {
            file.createNewFile();
        }
        nl.serializeToFile(tree, file);
    }

    @Test
    public void testDeserialize() throws IOException {
        NodeLang nl = NodeLang.Default;
        String temp = System.getenv("TEMP");
        File file = new File(temp + "/nl_serialized.node");
        System.out.println(file.getName());
        NodeTree tree = nl.deserializeFromFile(file);

        Interpreter in = new Interpreter();
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
