package opengal.syntax;

public interface IExpressionParser{
  <T> Expression<T> parse();
}
