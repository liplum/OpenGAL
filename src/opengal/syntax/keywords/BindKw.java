package opengal.syntax.keywords;

import opengal.syntax.Keyword;
import opengal.tree.BindNode;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;

public class BindKw extends Keyword {
    @Override
    public String getKeywordName() {
        return "Bind";
    }

    @Override
    public int getArgNumber() {
        return 1;
    }

    @Override
    public @NotNull StoryNode gen(@NotNull Object[] args) {
        BindNode bindNode = new BindNode();
        bindNode.boundName = (String) args[0];
        return bindNode;
    }
}
