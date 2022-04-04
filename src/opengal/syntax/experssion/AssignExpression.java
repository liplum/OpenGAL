package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public class AssignExpression<T> implements Expression<T>{
  public Expression<T> exp;
  public IdentExpression<T> ident;

  @Override
  public T calculate(IInterpreter interpreter){
    T result = exp.calculate(interpreter);
    interpreter.set(ident.key, result);
    return result;
  }

  @Override
  public String toString(){
    return ident + " = " + exp;
  }

  @Override
  public void serialize(DataOutput output) throws IOException {
    ident.serialize(output);
    exp.serialize(output);
  }

  @Override
  public void deserialize(DataInput input) throws IOException {
    ident.deserialize(input);
    exp.deserialize(input);
  }

  @NotNull
  @Override
  public Iterator<Expression<?>> iterator() {
    return SerializeUtils.varargsIt(exp,ident);
  }
}
