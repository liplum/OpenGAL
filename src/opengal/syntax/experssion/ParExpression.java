package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class ParExpression<T> implements Expression<T>{
  Expression<T> expr;

  public ParExpression(Expression<T> expr){
    this.expr = expr;
  }

  @Override
  public T calculate(IInterpreter interpreter){
    return expr.calculate(interpreter);
  }

  @Override
  public String toString(){
    return "(" + expr + ")";
  }
}
