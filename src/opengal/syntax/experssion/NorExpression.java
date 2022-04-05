package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public final class  NorExpression implements Expression<Boolean>{
  public Expression<Boolean> exp;

  public NorExpression(Expression<Boolean> exp) {
    this.exp = exp;
  }

  public NorExpression() {
  }

  @Override
  public Boolean calculate(IInterpreter interpreter){
    return !exp.calculate(interpreter);
  }

  @Override
  public String toString(){
    return "!" + exp;
  }

  @Override
  public void serialize(DataOutput output) throws IOException {
    SerializeUtils.writeThisID(output,this);
    exp.serialize(output);
  }

  @Override
  public void deserialize(DataInput input) throws IOException {
    exp.deserialize(input);
  }

  @NotNull
  @Override
  public Iterator<Expression<?>> iterator() {
    return SerializeUtils.singleIt(exp);
  }
}
