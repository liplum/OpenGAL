package opengal.core;

import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodeTree {
    @NotNull
    private final List<Node> nodes;
    @NotNull
    private final Set<String> inputs = new HashSet<>();
    @NotNull
    private final HashMap<String, Object> metas = new HashMap<>();
    @Nullable
    public String fileName;
    @NotNull
    public String nothingName = "Nothing";

    public NodeTree(
            @NotNull List<Node> nodes
    ) {
        this.nodes = nodes;
    }

    public int getSize() {
        return nodes.size();
    }

    @NotNull
    public Node get(int index) {
        return nodes.get(index);
    }

    @NotNull
    public List<Node> getNodes() {
        return nodes;
    }

    @NotNull
    public HashMap<String, Object> getMetas() {
        return metas;
    }

    @NotNull
    public Set<String> getInputs() {
        return inputs;
    }

    public void addInput(String input) {
        inputs.add(input);
    }
}
