package opengal.core;

import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class StoryTree {
    @NotNull
    private final List<Node> nodes;
    @Nullable
    public Set<String> inputs;
    @Nullable
    public String fileName;
    public StoryTree(
            @NotNull List<Node> nodes
    ) {
        this.nodes = nodes;
        this.inputs = null;
    }

    public int getSize() {
        return nodes.size();
    }

    @NotNull
    public Node get(int index) {
        return nodes.get(index);
    }
}
