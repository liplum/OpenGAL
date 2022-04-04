package opengal.test;

import opengal.core.Interpreter;
import opengal.core.NodeTree;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedList;

@ExtendWith({Timing.class, Memory.class})
public class TestGalInterpreter {
    public NodeTree tree;
    public Interpreter in;
    public LinkedList<String> output = new LinkedList<>();
    public boolean silent = true;

    @Test
    public void test() {
        in.start();
        while (!in.isEnd()) {
            in.execute();
        }
    }

    @Test
    @Disabled
    public void benchmark() {
        for (int i = 0; i < 10000; i++) {
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
    public void genTree() {
     /*
        ArrayList<StoryNode> nodes = new ArrayList<>();
        BindNode bUnit = new BindNode();
        bUnit.boundName = "Unit";
        BindNode bPlayer = new BindNode();
        bPlayer.boundName = "Player";
        TextNode[] ts = new TextNode[8];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new TextNode();
            ts[i].textID = i;
        }
        OptionNode op = new OptionNode();
        op.optionCount = 2;
        OptionEndNode ope = new OptionEndNode();
        HashMap<Integer, String> opeGoto = new HashMap<>();
        ope.number2Destination = opeGoto;
        opeGoto.put(0, "Like");
        opeGoto.put(1, "DontLike");
        ReturnNode ret = new ReturnNode();
        //Main
        nodes.add(bUnit);      //0
        nodes.add(ts[0]);      //1
        nodes.add(ts[1]);      //2
        nodes.add(ts[2]);      //3
        nodes.add(op);         //4
        nodes.add(ope);        //5
        nodes.add(bUnit);      //6
        nodes.add(ts[3]);      //7
        nodes.add(bPlayer);    //8
        nodes.add(ts[4]);      //9
        nodes.add(bUnit);      //10
        nodes.add(ts[5]);      //11
        nodes.add(ret);        //12
        //Like
        nodes.add(bUnit);      //13
        nodes.add(ts[6]);      //14
        nodes.add(ret);        //15
        //DontLike
        nodes.add(bUnit);      //16
        nodes.add(ts[7]);      //17
        nodes.add(ret);        //18
        HashMap<String, Integer> b2i = new HashMap<>();
        b2i.put("Main", 0);
        b2i.put("Like", 12);
        b2i.put("DontLike", 15);
        tree = new StoryTree(nodes);
        in = new Interpreter();
        in.setTree(tree);
        if (!silent) {
            in.beforeExecute(() -> {
                output.add("[" + in.getCurIndex() + "]");
            });
            in.afterExecute(() -> {
                output.add("\n");
            });
            in.setTextHandler(id -> {
                output.add(in.getCurBound() + "'s text:" + id);
            });
            in.onBound(() -> {
                output.add("Binding:" + in.getCurBound());
            });
            in.setOptionHandler(number -> {
                output.add("Option Number:" + number);
                in.setSelected(0);
            });
            in.onEnd(() -> {
                output.add("End\n");
            });
        } else {
            Random random = new Random();
            in.setOptionHandler(number -> {
                in.setSelected(random.nextInt(number));
            });
        }
        in.setInput("Unit", "Unit#5978");
        in.setInput("Player", "Player#12138");
        if (!silent) {
            for (StoryNode node : tree.getNodes()) {
                System.out.println(node.getClass().getSimpleName());
            }
            System.out.flush();
        }*/
    }
/*
@file LandDay
@input Player
@input Unit
#
:bind @Unit
:text
:text
:text
:option 2
|-> Like
|-> DontLike
:bind @Unit
:text
:bind @Player
:text
:bind @Unit
:text

Like:
:bind @Unit
:text
:return
end Like

DontLike:
:bind @Unit
:text
:return
end DontLike
 */
}
