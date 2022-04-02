package opengal.excpetions;

public class ExpressionException extends RuntimeException{
  public final String info;

  public ExpressionException(String str){
    super(str);
    info = str;
  }
}
