package opengal.test;

import opengal.syntax.ExpressionParser;

import java.util.Arrays;
import java.util.List;

public class ExprTest{
  public static void main(String[] args){
    ExpressionParser parser = new ExpressionParser(List.of("@au", "=", "(", "@c", "+", "$u", ")", "*", "30"));
    System.out.println(parser.parse());
  }
}
