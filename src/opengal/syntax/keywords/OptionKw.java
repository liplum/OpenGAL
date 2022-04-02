package opengal.syntax.keywords;

import opengal.syntax.AnalysisContext;
import opengal.syntax.Keyword;
import opengal.tree.OptionNode;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OptionKw extends Keyword {
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
