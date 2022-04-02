package opengal.syntax;

import java.util.*;

public class ExpressionParser implements IExpressionParser{
  private static final String END = "$end";

  private static final HashSet<?> BIN_OPERATOR = new HashSet<>(
      Arrays.asList(
          "!=", ">=", "<=", "&&", "||"
      )
  );

  ArrayList<String> tokens = new ArrayList<>();

  HashSet<Character>[] priorityMap = new HashSet[10];

  public ExpressionParser(Collection<String> tokens){
    this.tokens.addAll(tokens);

    String symbol;
    for(int i = 1; i < tokens.size(); i++){
      if(this.tokens.get(i-1).length() == 1 && this.tokens.get(i).length() == 1
          && BIN_OPERATOR.contains(symbol = this.tokens.get(i-1) + this.tokens.get(i))){
        this.tokens.set(i-1, symbol);
        this.tokens.remove(i--);
      }
    }

    this.tokens.add(END);

    setParseUnities();
  }

  public void setParseUnities(){
    for(int i = 0; i < priorityMap.length; i++){
      priorityMap[i] = new HashSet<>();
    }
    priorityMap[0].add('!');
  }

  @Override
  public <T> Expression<T> parse(){
    return null;
  }

  protected static class Placeholder extends ParseUnit{

  }

  protected static class NorParser extends ParseUnit{

  }

  protected static abstract class ParseUnit{

    public Expression<?> parse(){
      return null;
    }

    public void read(String token){

    }
  }
}
