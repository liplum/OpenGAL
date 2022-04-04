package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

public class ConstantExpression<T> implements Expression<T>{
  public T value;

  @Override
  public T calculate(IInterpreter interpreter){
    return value;
  }

  @Override
  public String toString(){
    return value.toString();
  }

  @Override
  public void serialize(DataOutput output) throws IOException {
    SerializeUtils.serializeObj(output,value);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void deserialize(DataInput input) throws IOException {
    value = (T) SerializeUtils.deserializeObj(input);
  }

  @NotNull
  @Override
  public Iterator<Expression<?>> iterator() {
    return Collections.emptyIterator();
  }
}
