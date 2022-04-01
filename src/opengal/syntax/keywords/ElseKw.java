package opengal.syntax.keywords;

import opengal.syntax.Keyword;
import opengal.tree.ConditionNode;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElseKw extends Keyword {
    @Override
    public String getKeywordName() {
        return null;
    }

    @Override
    public int getArgNumber() {
        return 0;
    }

    @Override
    public boolean companionBy(String kwName) {
        return "If".equals(kwName);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    @Nullable
    public String symbolNeed() {
        return "JumpTo";
    }

    @Override
    @NotNull
    public StoryNode gen(@NotNull Object[] args) {
        // The first arg is a condition node
        ConditionNode conditionNode = (ConditionNode) args[0];
        conditionNode.falseDestination = (String) args[1];
        return conditionNode;
    }
}
