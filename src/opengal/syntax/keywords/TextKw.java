package opengal.syntax.keywords;

import opengal.syntax.Keyword;
import opengal.tree.StoryNode;
import opengal.tree.TextNode;
import org.jetbrains.annotations.NotNull;

public class TextKw extends Keyword {
    @Override
    public String getKeywordName() {
        return "Text";
    }

    @Override
    public int getArgNumber() {
        return 1;
    }

    @Override
    public @NotNull StoryNode gen(@NotNull Object[] args) {
        TextNode textNode = new TextNode();
        textNode.textID = (int) args[0];
        return textNode;
    }
}
