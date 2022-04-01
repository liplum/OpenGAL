package opengal.syntax.keywords;

import opengal.syntax.Keyword;
import opengal.tree.JumpNode;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;

public class JumpKw extends Keyword {
    @Override
    public String getKeywordName() {
        return "Jump";
    }

    @Override
    public int getArgNumber() {
        return 1;
    }

    @Override
    public @NotNull StoryNode gen(@NotNull Object[] args) {
        JumpNode jumpNode = new JumpNode();
        jumpNode.destination = (String) args[0];
        return jumpNode;
    }
}
