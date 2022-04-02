package opengal.syntax.keywords;

import opengal.syntax.AnalysisContext;
import opengal.syntax.Keyword;
import opengal.tree.ConditionNode;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IfKw extends Keyword {

    @Override
    public void check(AnalysisContext context){

    }

    @Override
    public String generate(){
        return null;
    }

    @Override
    public void product(AnalysisContext context){

    }

    @Override
    public boolean headMatcher(String token){
        return false;
    }
}
