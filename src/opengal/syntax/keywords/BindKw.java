package opengal.syntax.keywords;

import opengal.excpetions.KeywordException;
import opengal.syntax.AnalysisContext;
import opengal.syntax.Keyword;
import opengal.tree.BindNode;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;

public class BindKw extends Keyword{

    @Override
    public void check(AnalysisContext context){
      if(tokens.size() == 1)
          throw new KeywordException("bind target was missing " + generateError(context));
      if(tokens.size() > 2)
          throw new KeywordException("unexpected token: \"" + generate() + ">" + tokens.get(2) + "<\" " + generateError(context));
      if(!tokens.get(1).startsWith("@"))
          throw new KeywordException("target require is reference(missing \"@\"): \":bind >" + tokens.get(1) + "<\" " + generateError(context));
      if(!context.metas.contains(tokens.get(1)))
          throw new KeywordException("reference target " + tokens.get(1) + " was not found" + generateError(context));
    }

    @Override
    public String generate(){
        return ":bind " + tokens.get(1);
    }

    @Override
    public void product(AnalysisContext context){
        BindNode node = new BindNode();
        node.boundName = tokens.get(1);
        context.putNode(this, node);
    }

    @Override
    public boolean headMatcher(String token){
        return token.equals(":bind");
    }
}
