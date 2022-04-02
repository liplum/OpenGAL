package opengal.syntax.keywords;

import opengal.excpetions.KeywordException;
import opengal.syntax.AnalysisContext;
import opengal.syntax.Keyword;

public class TextKw extends Keyword {

    @Override
    public void check(AnalysisContext context) {
        if (tokens.size() > 1)
            throw new KeywordException("unexpected token: \":text >" + tokens.get(1) + "<\" " + generateError(context));
    }

    @Override
    public String generate() {
        return ":text";
    }

    @Override
    public void product(AnalysisContext context) {
        int index = context.countOf(this);
       /* TextNode node = new TextNode();
        node.textID = index;
        context.putNode(this, node);*/
    }

    @Override
    public boolean headMatcher(String token) {
        return token.equals(":text");
    }
}
