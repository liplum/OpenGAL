package opengal.nl;

import opengal.core.NodeTree;
import opengal.core.OpenGAL;
import opengal.exceptions.NoSuchNodeException;
import opengal.exceptions.NodeLangException;
import opengal.tree.*;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

public class NodeLang implements INodeLang {
    public static final byte
            Action = 0,
            Condition = 1,
            Bind = 2,
            Unbind = 3,
            Return = 4,
            Stop = 5,
            BlockEnd = 6,
            BlockEntry = 7,
            Jump = 8,
            Calcu = 9,
            Yield = 10;
    public static final String
            FileMeta = "file",
            InputMeta = "input";
    @NotNull
    private static final Map<Byte, Supplier<Node>> StandardID2Node;
    @NotNull
    private static final Map<Class<? extends Node>, Byte> StandardNode2ID;

    static {
        StandardID2Node = new HashMap<>();
        StandardID2Node.put(Action, ActionNode::new);
        StandardID2Node.put(Condition, ConditionNode::new);
        StandardID2Node.put(Bind, BindNode::new);
        StandardID2Node.put(Unbind, () -> UnbindNode.X);
        StandardID2Node.put(Return, () -> ReturnNode.X);
        StandardID2Node.put(Stop, () -> StopNode.X);
        StandardID2Node.put(BlockEnd, () -> BlockEndNode.X);
        StandardID2Node.put(BlockEntry, BlockEntryNode::new);
        StandardID2Node.put(Jump, JumpNode::new);
        StandardID2Node.put(Calcu, CalcuNode::new);
        StandardID2Node.put(Yield, YieldNode::new);

        StandardNode2ID = new HashMap<>();
        StandardNode2ID.put(ActionNode.class, Action);
        StandardNode2ID.put(ConditionNode.class, Condition);
        StandardNode2ID.put(BindNode.class, Bind);
        StandardNode2ID.put(UnbindNode.class, Unbind);
        StandardNode2ID.put(ReturnNode.class, Return);
        StandardNode2ID.put(StopNode.class, Stop);
        StandardNode2ID.put(BlockEndNode.class, BlockEnd);
        StandardNode2ID.put(BlockEntryNode.class, BlockEntry);
        StandardNode2ID.put(JumpNode.class, Jump);
        StandardNode2ID.put(CalcuNode.class, Calcu);
        StandardNode2ID.put(YieldNode.class, Yield);
    }

    @NotNull
    public static final NodeLang Default = new NodeLang(
            StandardID2Node, StandardNode2ID
    );

    public Map<Byte, Supplier<Node>> id2Nodes;
    public Map<Class<? extends Node>, Byte> node2ID;

    public NodeLang(
            Map<Byte, Supplier<Node>> id2Nodes,
            Map<Class<? extends Node>, Byte> node2ID
    ) {
        this.id2Nodes = id2Nodes;
        this.node2ID = node2ID;
    }


    protected void writeMeta(@NotNull NodeTree tree, @NotNull DataOutput output) throws IOException {
        // For file
        output.writeUTF(FileMeta);
        String file = tree.file;
        if (file == null) {
            output.writeBoolean(false);
        } else {
            output.writeBoolean(true);
            output.writeUTF(file);
        }

        // For inputs
        output.writeUTF(InputMeta);
        Set<String> inputs = tree.getInputs();
        output.writeInt(inputs.size());
        for (String input : inputs) {
            output.writeUTF(input);
        }

        // For other metas
        Map<String, Set<String>> metas = tree.getMetas();
        output.writeInt(metas.size());
        for (Map.Entry<String, Set<String>> pair : metas.entrySet()) {
            String name = pair.getKey();
            Set<String> values = pair.getValue();
            output.writeUTF(name);
            output.writeInt(values.size());
            for (String value : values) {
                output.writeUTF(value);
            }
        }
    }

    protected void readMeta(@NotNull NodeTree tree, @NotNull DataInput input) throws IOException {
        // For file
        String fileMeta = input.readUTF();
        if (!fileMeta.equals(FileMeta))
            throw new NodeLangException("@" + FileMeta + " doesn't match");
        boolean hasFileName = input.readBoolean();
        if (hasFileName)
            tree.file = input.readUTF();
        // For inputs
        String inputMeta = input.readUTF();
        if (!inputMeta.equals(InputMeta))
            throw new NodeLangException("@" + InputMeta + " doesn't match");
        int inputSize = input.readInt();
        Set<String> inputs = new HashSet<>(inputSize);
        for (int i = 0; i < inputSize; i++) {
            inputs.add(input.readUTF());
        }
        tree.setInputs(inputs);
        // For other metas
        int metaSize = input.readInt();
        Map<String, Set<String>> metas = new HashMap<>(metaSize);
        for (int i = 0; i < metaSize; i++) {
            String name = input.readUTF();
            int valueSize = input.readInt();
            Set<String> values = new HashSet<>(valueSize);
            for (int j = 0; j < valueSize; j++) {
                values.add(input.readUTF());
            }
            metas.put(name, values);
        }
        tree.setMetas(metas);
    }

    public void serialize(@NotNull NodeTree tree, @NotNull DataOutput output) {
        try {
            output.writeInt(0xCAFE_BABA);
            output.writeByte(OpenGAL.Version);//Version
            output.writeByte(0);// 0x0000_0000
            output.writeByte(0);
            writeMeta(tree, output);
            output.writeInt(tree.getSize());
            for (Node node : tree.getNodes()) {
                Class<? extends Node> clz = node.getClass();
                Byte id = node2ID.get(clz);
                if (id == null) {
                    throw new NoSuchNodeException(clz.getName());
                }
                output.writeByte(id);
                node.serialize(output);
            }
        } catch (Exception e) {
            throw new NodeLangException(e);
        }
    }

    @NotNull
    public NodeTree deserialize(@NotNull DataInput input) {
        try {
            int magic = input.readInt();
            if (magic != 0xCAFE_BABA) throw new NodeLangException("Not a node file.");
            input.readByte();//Version
            input.readByte();// 0x0000_0000
            int skip = input.readByte();// Extra data size
            if (skip > 0) input.skipBytes(skip);
            NodeTree tree = new NodeTree();
            readMeta(tree, input);
            int nodeLen = input.readInt();
            ArrayList<Node> nodes = new ArrayList<>(nodeLen);
            for (int i = 0; i < nodeLen; i++) {
                Byte id = input.readByte();
                Supplier<Node> nodeGen = id2Nodes.get(id);
                if (nodeGen == null) {
                    throw new NoSuchNodeException("Can't map the node of " + id);
                }
                Node node = nodeGen.get();
                node.deserialize(input);
                nodes.add(node);
            }
            tree.setNodes(nodes);
            return tree;
        } catch (NodeLangException ne) {
            throw ne;
        } catch (Exception e) {
            throw new NodeLangException(e);
        }
    }
}
