package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class IdentExpression<T> implements Expression<T>{
  public String key;

  @Override
  public T calculate(IInterpreter interpreter){
    return interpreter.get(key);
  }
}
