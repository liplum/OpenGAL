package opengal.syntax.experssion;

import opengal.syntax.Expression;

public class StringMergeExpression implements Expression<String>{
  Expression<Object> a;
  Expression<Object> b;

  @Override
  public String calculate(){
    String str = String.valueOf(a.calculate());
    return str + b.calculate();
  }
}
