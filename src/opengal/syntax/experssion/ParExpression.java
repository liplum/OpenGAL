package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public final class ParExpression<T> implements Expression<T>{
  public Expression<T> expr;

  public ParExpression() {
  }

  public ParExpression(Expression<T> expr){
    this.expr = expr;
  }

  @Override
  public T calculate(IInterpreter interpreter){
    return expr.calculate(interpreter);
  }

  @Override
  public String toString(){
    return "(" + expr + ")";
  }

  @Override
  public void serialize(DataOutput output) throws IOException {
    SerializeUtils.writeThisID(output,this);
    expr.serialize(output);
  }

  @Override
  public void deserialize(DataInput input) throws IOException {
    expr = SerializeUtils.readByID(input.readByte());
    expr.deserialize(input);
  }
  @NotNull
  @Override
  public Iterator<Expression<?>> iterator() {
    return SerializeUtils.singleIt(expr);
  }
}
