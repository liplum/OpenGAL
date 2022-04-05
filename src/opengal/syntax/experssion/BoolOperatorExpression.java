package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public final class BoolOperatorExpression implements Expression<Boolean>{
  private static final String[] opCodeMap = {
      "&&",
      "||"
  };

  public static final int
    And = 0,Or = 1;

  public Expression<Boolean> a, b;
  public int opCode;

  public BoolOperatorExpression(int opCode,Expression<Boolean> a, Expression<Boolean> b) {
    this.opCode = opCode;
    this.a = a;
    this.b = b;
  }

  public BoolOperatorExpression() {
  }

  @Override
  public Boolean calculate(IInterpreter interpreter){
    switch(opCode){
      case 0: return a.calculate(interpreter) && b.calculate(interpreter); // &&
      case 1: return a.calculate(interpreter) || b.calculate(interpreter); // ||
      default: throw new RuntimeException("unknown opcode: " + opCode);
    }
  }

  public String getOperator(){
    return opCodeMap[opCode];
  }

  @Override
  public String toString(){
    return a + " " + getOperator() + " " + b;
  }

  @Override
  public void serialize(DataOutput output) throws IOException {
    SerializeUtils.writeThisID(output,this);
    output.writeByte(opCode);
    a.serialize(output);
    b.serialize(output);
  }

  @Override
  public void deserialize(DataInput input) throws IOException {
    opCode = input.readByte();
    a = SerializeUtils.readByID(input.readByte());
    a.deserialize(input);
    b = SerializeUtils.readByID(input.readByte());
    b.deserialize(input);
  }
  @NotNull
  @Override
  public Iterator<Expression<?>> iterator() {
    return SerializeUtils.varargsIt(a,b);
  }
}
