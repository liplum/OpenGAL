package opengal.core;

import opengal.excpetions.NoSuchBlockException;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class StoryTree {
    @NotNull
    private final List<Node> nodes;

    public StoryTree(
            @NotNull List<Node> nodes
    ) {
        this.nodes = nodes;
    }

    public int getSize() {
        return nodes.size();
    }

    /**
     * Gets the index which this block starts
     * @param blockName name
     * @return the index which this block starts
     * @exception NoSuchBlockException raises when this block isn't in this tree.
     */
    public int getStartIndex(@NotNull String blockName) {
        Integer index = name2Index.get(blockName);
        if (index == null) {
            throw new NoSuchBlockException(blockName + " block not found.");
        }
        return index;
    }

    @NotNull
    public Node get(int index) {
        return nodes.get(index);
    }
}
