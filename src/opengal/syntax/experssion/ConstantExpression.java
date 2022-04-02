package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class ConstantExpression<T> implements Expression<T>{
  public T value;

  @Override
  public T calculate(IInterpreter interpreter){
    return value;
  }
}
