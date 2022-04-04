package opengal.nl;

import opengal.core.NodeTree;
import opengal.excpetions.NoSuchNodeException;
import opengal.excpetions.NodeLangException;
import opengal.tree.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class NodeLang implements INodeLang {
    public static final HashMap<String, Supplier<Node>> StandardID2Node;
    public static final HashMap<Class<? extends Node>, String> StandardNode2ID;

    static {
        StandardID2Node = new HashMap<>();
        StandardID2Node.put("Action", ActionNode::new);
        StandardID2Node.put("If", ConditionNode::new);
        StandardID2Node.put("Bind", BindNode::new);
        StandardID2Node.put("Unbind", UnbindNode::new);
        StandardID2Node.put("Return", ReturnNode::new);
        StandardID2Node.put("Stop", StopNode::new);
        StandardID2Node.put("BlockEnd", BlockEndNode::new);
        StandardID2Node.put("BlockEntry", BlockEntryNode::new);
        StandardID2Node.put("Jump", JumpNode::new);

        StandardNode2ID = new HashMap<>();
        StandardNode2ID.put(ActionNode.class, "Action");
        StandardNode2ID.put(ConditionNode.class, "If");
        StandardNode2ID.put(BindNode.class, "Bind");
        StandardNode2ID.put(UnbindNode.class, "Unbind");
        StandardNode2ID.put(ReturnNode.class, "Return");
        StandardNode2ID.put(StopNode.class, "Stop");
        StandardNode2ID.put(BlockEndNode.class, "BlockEnd");
        StandardNode2ID.put(BlockEntryNode.class, "BlockEntry");
        StandardNode2ID.put(JumpNode.class, "Jump");
    }

    public static final NodeLang Default = new NodeLang(
            StandardID2Node,StandardNode2ID
    );

    public HashMap<String, Supplier<Node>> id2Nodes;
    public HashMap<Class<? extends Node>, String> node2ID;

    public NodeLang(
            HashMap<String, Supplier<Node>> id2Nodes,
            HashMap<Class<? extends Node>, String> node2ID
    ) {
        this.id2Nodes = id2Nodes;
        this.node2ID = node2ID;
    }

    public void serialize(NodeTree tree, DataOutput output) {
        try {
            output.writeInt(tree.getSize());
            for (Node node : tree.getNodes()) {
                Class<? extends Node> clz = node.getClass();
                String id = node2ID.get(clz);
                if(id == null){
                    throw new NoSuchNodeException(clz.getName());
                }
                output.writeUTF(id);
                node.serialize(output);
            }
        } catch (Exception e) {
            throw new NodeLangException(e);
        }
    }

    public NodeTree deserialize(DataInput input) {
        try {
            int nodeLen = input.readInt();
            ArrayList<Node> nodes = new ArrayList<>(nodeLen);
            for (int i = 0; i < nodeLen; i++) {
                String id = input.readUTF();
                Supplier<Node> nodeGen = id2Nodes.get(id);
                if (nodeGen == null) {
                    throw new NoSuchNodeException(id);
                }
                Node node = nodeGen.get();
                node.deserialize(input);
                nodes.add(node);
            }
            return new NodeTree(nodes);
        } catch (Exception e) {
            throw new NodeLangException(e);
        }
    }
}
