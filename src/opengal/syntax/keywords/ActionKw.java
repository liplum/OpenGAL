package opengal.syntax.keywords;

import opengal.syntax.Keyword;
import opengal.tree.ActionNode;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActionKw extends Keyword {
    @Override
    public String getKeywordName() {
        return "Action";
    }

    @Override
    public int getArgNumber() {
        return 1;
    }

    @Override
    public Object[] bakeArgs(Object[] original) {
        return super.bakeArgs(original);
    }

    @Override
    public boolean customHandleArgs() {
        return super.customHandleArgs();
    }

    @Override
    @Nullable
    public Object[] handleArgs(String args) {
        return super.handleArgs(args);
    }

    @Override
    public @NotNull StoryNode gen(@NotNull Object[] args) {
        // 1st: action name
        // 2nd?: arg1
        ActionNode actionNode = new ActionNode();
        actionNode.actionName = (String) args[0];
        Object[] actionArgs = new Object[args.length - 1];
        System.arraycopy(actionArgs, 1, actionArgs, 0, args.length - 1);
        actionNode.args = actionArgs;
        return actionNode;
    }
}
