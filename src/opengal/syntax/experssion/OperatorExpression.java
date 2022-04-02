package opengal.syntax.experssion;

import opengal.syntax.Expression;

public class OperatorExpression implements Expression<Integer>{
  public Expression<Integer> a, b;
  public int opCode;

  @Override
  public Integer calculate(){
    switch(opCode){
      case 0: return a.calculate() + b.calculate(); // +
      case 1: return a.calculate() - b.calculate(); // -
      case 2: return a.calculate() * b.calculate(); // *
      case 3: return a.calculate() / b.calculate(); // /
      case 4: return a.calculate() % b.calculate(); // %
      default: throw new RuntimeException("unknow opcode: " + opCode);
    }
  }
}
