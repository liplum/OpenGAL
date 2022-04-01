package opengal.syntax.keywords;

import opengal.syntax.Keyword;
import opengal.tree.ConditionNode;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IfKw extends Keyword {
    public ArrayList<String> companion;

    public IfKw() {
        companion = new ArrayList<>();
        companion.add("Else");
    }

    @Override
    public String getKeywordName() {
        return "If";
    }

    @Override
    public int getArgNumber() {
        return 1;
    }

    @Override
    @Nullable
    public List<String> companionKeyword() {
        return companion;
    }

    @Override
    @Nullable
    public String symbolNeed() {
        return "JumpTo";
    }

    @Override
    public @NotNull StoryNode gen(@NotNull Object[] args) {
        ConditionNode conditionNode = new ConditionNode();
        conditionNode.conditionName = (String) args[0];
        conditionNode.trueDestination = (String) args[1];
        return conditionNode;
    }
}
