package opengal.syntax;


import java.util.ArrayList;

public abstract class Keyword{
  public ArrayList<String> tokens = new ArrayList<>();

  public abstract void check(AnalysisContext context);

  public abstract String generate();

  public abstract void product(AnalysisContext context);

  public abstract boolean headMatcher(String token);

  public void associate(Keyword associated){}

  public void read(String nextToken){
    tokens.add(nextToken);
  }

  public void finish(){
    tokens.clear();
  }

  protected String generateError(AnalysisContext context){
    return context.file.getName() + ":" + context.line;
  }


}
