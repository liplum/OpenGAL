package opengal.syntax.keywords;

import opengal.syntax.AnalysisContext;
import opengal.syntax.Keyword;
import opengal.tree.ReturnNode;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;

public class ReturnKw extends Keyword {
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
