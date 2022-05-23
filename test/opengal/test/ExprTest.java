package opengal.test;

import opengal.experssion.Expression;
import opengal.experssion.ExpressionParser;

import java.util.Arrays;
import java.util.List;

public class ExprTest{
  public static void main(String[] args){
    ExpressionParser parser = new ExpressionParser(List.of("true", "&&", "!", "(", "\"1\"", "!=", "\"abc\"", ")"));
    Expression<?> exp = parser.parse();
    System.out.println(exp);
  }
}
