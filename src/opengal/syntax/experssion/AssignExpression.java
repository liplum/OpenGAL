package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class AssignExpression<T> implements Expression<T>{
  public Expression<T> exp;
  public IdentExpression<T> ident;

  @Override
  public T calculate(IInterpreter interpreter){
    T result = exp.calculate(interpreter);
    interpreter.set(ident.key, result);
    return result;
  }

  @Override
  public String toString(){
    return ident + " = " + exp;
  }
}
