package opengal.core;

import opengal.tree.StoryNode;

import java.util.List;

public class StoryTree {
    private final List<StoryNode> nodes;

    public StoryTree(List<StoryNode> nodes) {
        this.nodes = nodes;
    }

    public int getSize() {
        return nodes.size();
    }

    public StoryNode get(int index) {
        return nodes.get(index);
    }
}
