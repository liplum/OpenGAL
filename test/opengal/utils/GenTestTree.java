package opengal.utils;

import opengal.core.NodeTree;
import opengal.experssion.IdentExpression;
import opengal.tree.*;

import java.util.ArrayList;

public class GenTestTree {
    /*
    @file TestGAL
    @input Plum

    [ 0]:action output(10)
    [ 1]:action output(1)
    [ 2]:if @IsTrue
    [ 3]    :action output("Wow, you got true!")
    [ 4]    :entry WhenTrue
    [ 5]:else
    [ 6]    :entry WhenFalse
    [ 7]    :action output("Oh no, you got false... TAT")
    [  ]:end
    [ 8]:action output("But you're still alive!")

    [ 9]WhenTrue:
    [10]    :bind @Plum
    [11]    :action output("YES!")
    [12]    :return
    [13]end WhenTrue

    [14]WhenFalse:
    [15]    :action output("NO!")
    [16]    :return
    [17]end WhenFalse
    [18]
     */
    public static NodeTree genTree() {
        ArrayList<Node> nodes = new ArrayList<>();
        ActionNode output10 = new ActionNode();
        output10.actionName = "output";
        output10.args = new Object[]{10};
        ActionNode output1 = new ActionNode();
        output1.actionName = "output";
        output1.args = new Object[]{1};
        ActionNode a3 = new ActionNode();
        a3.actionName = "output";
        a3.args = new Object[]{"But you're still alive!"};
        ActionNode a4 = new ActionNode();
        a4.actionName = "output";
        a4.args = new Object[]{"YES!"};
        ActionNode a5 = new ActionNode();
        a5.actionName = "output";
        a5.args = new Object[]{"NO!"};
        ConditionNode ifTrue = new ConditionNode();
        ifTrue.condition = new IdentExpression<>("IsTrue");
        ifTrue.trueDestination = 3;
        ifTrue.falseDestination = 6;
        BindNode bPlum = new BindNode();
        bPlum.boundName = "Plum";
        ActionNode outputWow = new ActionNode();
        outputWow.actionName = "output";
        outputWow.args = new Object[]{"Wow, you got true!"};
        ActionNode a7 = new ActionNode();
        a7.actionName = "output";
        a7.args = new Object[]{"Oh no, you got false... TAT"};
        BlockEntryNode entryTrue = new BlockEntryNode();
        entryTrue.blockHead = 9;
        BlockEntryNode entryFalse = new BlockEntryNode();
        entryFalse.blockHead = 14;
        JumpNode jumpIfEnd = new JumpNode();
        jumpIfEnd.destination = 8;
        JumpNode jumpTrueEnd = new JumpNode();
        jumpTrueEnd.destination = 14;
        JumpNode jumpFalseEnd = new JumpNode();
        jumpFalseEnd.destination = 18;
        //Main
        nodes.add(output10);            //0
        nodes.add(output1);             //1
        nodes.add(ifTrue);              //2
        nodes.add(outputWow);       //3
        nodes.add(entryTrue);       //4
        nodes.add(jumpIfEnd);           //5
        nodes.add(entryFalse);      //6
        nodes.add(a7);              //7
        nodes.add(a3);                  //8

        //WhenTrue
        nodes.add(jumpTrueEnd);         //9     =>14
        nodes.add(bPlum);           //10
        nodes.add(a4);              //11
        nodes.add(ReturnNode.X);    //12
        nodes.add(BlockEndNode.X);      //13

        //WhenFalse
        nodes.add(jumpFalseEnd);        //14    =>18
        nodes.add(a5);              //15
        nodes.add(ReturnNode.X);    //16
        nodes.add(BlockEndNode.X);      //17

        nodes.add(StopNode.X);          //18
        NodeTree tree = new NodeTree(nodes);
        tree.fileName = "TestGAL";
        tree.addInput("Plum");
        return tree;
    }
}
