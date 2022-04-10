package opengal.core;

import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeTree {
    private List<Node> nodes;
    @NotNull
    private Set<String> inputs = Collections.emptySet();
    @NotNull
    private Map<String, Set<String>> metas = Collections.emptyMap();
    @Nullable
    public String file;

    public NodeTree(@NotNull List<Node> nodes) {
        this.nodes = nodes;
    }

    public NodeTree() {
        this.nodes = Collections.emptyList();
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

    public void setNodes(@NotNull List<Node> nodes) {
        this.nodes = nodes;
    }

    @NotNull
    public Map<String, Set<String>> getMetas() {
        return metas;
    }

    public void setInputs(@NotNull Set<String> inputs) {
        this.inputs = inputs;
    }

    public void setMetas(@NotNull Map<String, Set<String>> metas) {
        this.metas = metas;
    }

    @NotNull
    public Set<String> getInputs() {
        return inputs;
    }
}
