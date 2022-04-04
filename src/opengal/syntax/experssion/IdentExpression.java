package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

public class IdentExpression<T> implements Expression<T>{
  public String key;

  @Override
  public T calculate(IInterpreter interpreter){
    return interpreter.get(key);
  }

  @Override
  public String toString(){
    return key;
  }

  @Override
  public void serialize(DataOutput output) throws IOException {
    output.writeUTF(key);
  }

  @Override
  public void deserialize(DataInput input) throws IOException {
    key = input.readUTF();
  }

  @NotNull
  @Override
  public Iterator<Expression<?>> iterator() {
    return Collections.emptyIterator();
  }
}
