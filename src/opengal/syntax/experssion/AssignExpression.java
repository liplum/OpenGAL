package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class AssignExpression<T> implements Expression<T>{
  public Expression<T> exp;
  public String key;

  @Override
  public T calculate(IInterpreter interpreter){
    T result = exp.calculate(interpreter);
    interpreter.set(key, result);
    return result;
  }
}
