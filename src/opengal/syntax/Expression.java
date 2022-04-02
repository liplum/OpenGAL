package opengal.syntax;

import opengal.core.IInterpreter;

public interface Expression<Type>{
  Type calculate(IInterpreter interpreter);

  String toString();
}
