package opengal.syntax.keywords;

import opengal.syntax.Keyword;
import opengal.tree.ReturnNode;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;

public class ReturnKw extends Keyword {

    @Override
    public String getKeywordName() {
        return "Return";
    }

    @Override
    public int getArgNumber() {
        return 0;
    }

    @Override
    public @NotNull StoryNode gen(@NotNull Object[] args) {
        return new ReturnNode();
    }
}
