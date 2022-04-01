package opengal.syntax.keywords;

import opengal.syntax.Keyword;
import opengal.tree.OptionNode;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OptionKw extends Keyword {
    @Override
    public String getKeywordName() {
        return "Option";
    }

    @Override
    public int getArgNumber() {
        return 1;
    }

    @Override
    @Nullable
    public String symbolNeed() {
        return "JumpTo";
    }

    @Override
    public @NotNull StoryNode gen(@NotNull Object[] args) {
        OptionNode optionNode = new OptionNode();
        optionNode.optionCount = (int) args[0];
        return optionNode;
    }
}
