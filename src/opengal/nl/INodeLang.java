package opengal.nl;

import opengal.core.NodeTree;

import java.io.*;

public interface INodeLang {

    void serialize(NodeTree tree, DataOutput output);

    default void serializeTo(NodeTree tree, OutputStream output) throws IOException {
        try (DataOutputStream stream = new DataOutputStream(output)) {
            serialize(tree, stream);
        }
    }

    default void serializeToFile(NodeTree tree, File file) throws IOException {
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(file))) {
            serialize(tree, stream);
        }
    }

    NodeTree deserialize(DataInput input);

    default NodeTree deserializeFrom(InputStream input) throws IOException {
        try (DataInputStream stream = new DataInputStream(input)) {
            return deserialize(stream);
        }
    }

    default NodeTree deserializeFromFile(File file) throws IOException {
        try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            return deserialize(stream);
        }
    }
}
