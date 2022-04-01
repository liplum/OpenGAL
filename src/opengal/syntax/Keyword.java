package opengal.syntax;


import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Keyword {
    public abstract String getKeywordName();

    public abstract int getArgNumber();

    @Nullable
    public List<String> companionKeyword() {
        return null;
    }

    public boolean companionBy(String kwName) {
        return false;
    }

    public boolean isSingle() {
        return true;
    }

    public Object[] bakeArgs(Object[] original) {
        return original;
    }

    public boolean customHandleArgs(){
        return false;
    }

    @Nullable
    public String symbolNeed(){
        return null;
    }

    @Nullable
    public Object[] handleArgs(String args){
        return null;
    }

    @NotNull
    public abstract StoryNode gen(@NotNull Object[] args);
}
