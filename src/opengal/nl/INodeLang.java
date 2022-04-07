package opengal.nl;

import opengal.core.NodeTree;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public interface INodeLang {

    void serialize(@NotNull NodeTree tree, @NotNull DataOutput output);

    default void serializeTo(NodeTree tree, OutputStream output) throws IOException {
        try (DataOutputStream stream = new DataOutputStream(output)) {
            serialize(tree, stream);
        }
    }

    default void serializeToFile(@NotNull NodeTree tree, @NotNull File file) throws IOException {
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(file))) {
            serialize(tree, stream);
        }
    }

    @NotNull
    NodeTree deserialize(@NotNull DataInput input);

    default NodeTree deserializeFrom(@NotNull InputStream input) throws IOException {
        try (DataInputStream stream = new DataInputStream(input)) {
            return deserialize(stream);
        }
    }

    @NotNull
    default NodeTree deserializeFromFile(@NotNull File file) throws IOException {
        try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            return deserialize(stream);
        }
    }
}
