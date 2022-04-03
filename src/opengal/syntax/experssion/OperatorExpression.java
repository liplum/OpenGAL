package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class OperatorExpression implements Expression<Integer>{
  private static final String[] opCodeMap = {
      "+",
      "-",
      "*",
      "/",
      "%"
  };

  public Expression<Integer> a, b;
  public int opCode;

  @Override
  public Integer calculate(IInterpreter interpreter){
    switch(opCode){
      case 0: return a.calculate(interpreter) + b.calculate(interpreter); // +
      case 1: return a.calculate(interpreter) - b.calculate(interpreter); // -
      case 2: return a.calculate(interpreter) * b.calculate(interpreter); // *
      case 3: return a.calculate(interpreter) / b.calculate(interpreter); // /
      case 4: return a.calculate(interpreter) % b.calculate(interpreter); // %
      default: throw new RuntimeException("unknow opcode: " + opCode);
    }
  }

  public String getOperator(){
    return opCodeMap[opCode];
  }

  @Override
  public String toString(){
    return a + " " + getOperator() + " " + b;
  }
}
