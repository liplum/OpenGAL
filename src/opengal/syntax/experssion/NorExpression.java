package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class NorExpression implements Expression<Boolean>{
  public Expression<Boolean> exp;

  @Override
  public Boolean calculate(IInterpreter interpreter){
    return !exp.calculate(interpreter);
  }
}
