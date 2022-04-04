package opengal.test;

import opengal.syntax.ExpressionParser;

import java.util.Arrays;
import java.util.List;

public class ExprTest{
  public static void main(String[] args){
    ExpressionParser parser = new ExpressionParser(List.of("@au", "=", "(", "@c", "*", "(", "$u", "+", "43", ")", ")", "*", "30", "+", "6", ">=", "74"));
    long nano = System.nanoTime();
    parser.parse();
    System.out.println(System.nanoTime() - nano);
  }
}
