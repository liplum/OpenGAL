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
    public static final HashMap<String, Supplier<Node>> Standard;

    static {
        Standard = new HashMap<>();
        Standard.put("Action", ActionNode::new);
        Standard.put("If", ConditionNode::new);
        Standard.put("Bind", BindNode::new);
        Standard.put("Unbind", UnbindNode::new);
        Standard.put("Return", ReturnNode::new);
        Standard.put("Stop", StopNode::new);
        Standard.put("BlockEnd", BlockEndNode::new);
        Standard.put("BlockEntry", BlockEntryNode::new);
        Standard.put("Jump", JumpNode::new);
    }

    public static final NodeLang Default = new NodeLang(Standard);

    public HashMap<String, Supplier<Node>> id2Nodes;

    public NodeLang(HashMap<String, Supplier<Node>> id2Nodes) {
        this.id2Nodes = id2Nodes;
    }

    public void serialize(NodeTree tree, DataOutput output) {
        try {
            output.writeInt(tree.getSize());
            for (Node node : tree.getNodes()) {
                output.writeUTF(node.getIdentity());
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
