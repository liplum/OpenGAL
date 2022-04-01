package opengal.core;

import opengal.excpetions.NoSuchBlockException;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class StoryTree {
    @NotNull
    private final List<StoryNode> nodes;
    private final Map<String, Integer> name2Index;

    public StoryTree(
            @NotNull List<StoryNode> nodes,
            Map<String, Integer> name2Index
    ) {
        this.nodes = nodes;
        this.name2Index = name2Index;
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
    public StoryNode get(int index) {
        return nodes.get(index);
    }
}
