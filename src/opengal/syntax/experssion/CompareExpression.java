package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;

public class CompareExpression implements Expression<Boolean>{
  private static final String[] opCodeMap = {
      "==",
      "!=",
      ">",
      "<",
      ">=",
      "<="
  };

  public Expression<Object> a, b;
  public int opCode;

  @Override
  public Boolean calculate(IInterpreter interpreter){
    switch(opCode){
      case 0: return a.calculate(interpreter) == b.calculate(interpreter);// ==
      case 1: return a.calculate(interpreter) != b.calculate(interpreter);// !=
      case 2: {
        Object i = a.calculate(interpreter), j = a.calculate(interpreter);
        if(i instanceof Integer && j instanceof Integer){
          return (Integer) i > (Integer) j;// >
        }
        throw new RuntimeException("compare the non-number variable!");
      }
      case 3: {
        Object i = a.calculate(interpreter), j = a.calculate(interpreter);
        if(i instanceof Integer && j instanceof Integer){
          return (Integer) i < (Integer) j;// <
        }
        throw new RuntimeException("compare the non-number variable!");
      }
      case 4: {
        Object i = a.calculate(interpreter), j = a.calculate(interpreter);
        if(i instanceof Integer && j instanceof Integer){
          return (Integer) i >= (Integer) j;// >=
        }
        throw new RuntimeException("compare the non-number variable!");
      }
      case 5: {
        Object i = a.calculate(interpreter), j = a.calculate(interpreter);
        if(i instanceof Integer && j instanceof Integer){
          return (Integer) i <= (Integer) j;// <=
        }
        throw new RuntimeException("compare the non-number variable!");
      }
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
