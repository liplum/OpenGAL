package opengal.syntax;

import opengal.core.IInterpreter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Expression<Type> extends Iterable<Expression<?>>{
  Type calculate(IInterpreter interpreter);

  String toString();

  void serialize(DataOutput output) throws IOException;

  void deserialize(DataInput input) throws IOException;
}
