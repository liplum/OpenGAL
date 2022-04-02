package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class StringMergeExpression implements Expression<String>{
  Expression<Object> a;
  Expression<Object> b;

  @Override
  public String calculate(IInterpreter interpreter){
    String str = String.valueOf(a.calculate(interpreter));
    return str + b.calculate(interpreter);
  }
}
