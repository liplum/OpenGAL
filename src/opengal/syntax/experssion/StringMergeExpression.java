package opengal.syntax.experssion;

import opengal.core.IInterpreter;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

public class StringMergeExpression implements Expression<String>{
  public Expression<Object> a, b;

  @Override
  public String calculate(IInterpreter interpreter){
    String str = String.valueOf(a.calculate(interpreter));
    return str + b.calculate(interpreter);
  }

  @Override
  public String toString(){
    return a + ".." + b;
  }

  @Override
  public void serialize(DataOutput output) throws IOException {
    a.serialize(output);
    b.serialize(output);
  }

  @Override
  public void deserialize(DataInput input) throws IOException {
    a.deserialize(input);
    b.deserialize(input);
  }

  @NotNull
  @Override
  public Iterator<Expression<?>> iterator() {
    return SerializeUtils.varargsIt(a,b);
  }
}
