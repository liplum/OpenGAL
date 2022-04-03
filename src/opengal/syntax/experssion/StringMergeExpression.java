package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class StringMergeExpression implements Expression<String>{
  public Expression<Object> a, b;

  @Override
  public String calculate(IInterpreter interpreter){
    String str = String.valueOf(a.calculate(interpreter));
    return str + b.calculate(interpreter);
  }

  @Override
  public String toString(){
    return a + ".." + b;
  }
}
