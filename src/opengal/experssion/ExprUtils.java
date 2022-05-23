package opengal.experssion;

import opengal.exceptions.ExprTokenSplitException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;

import static opengal.experssion.ExprUtils.TokenState.*;
import static opengal.experssion.ExprUtils.TokenType.*;

public class ExprUtils {
    private static final HashSet<Character> OperatorIndicators = new HashSet<>(Arrays.asList(
            '+', '-', '*', '/', '%', '=', '!', '>', '<', '&', '|', '.'
    ));
    private static final HashSet<String> Operators = new HashSet<>(Arrays.asList(
            "+", "-", "*", "/", "%", "=", "==", "!", ">", "<", "!=", ">=", "<=", "&&", "||", ".."
    ));
    private static final int MaxOpLen = 2;
    private static final char LeftPar = '(';
    private static final char RightPar = ')';
    private static final char ReferenceIndicator = '@';
    /**
     * Only Allows the ASCII quote.
     */
    private static final char StringIndicator = '"';

    /**
     * Splits the {@code expr} into tokens.<br/>
     * Only allows ASCII characters except for the quoted string.
     *
     * @param expr the string of expression
     * @return the tokens
     */
    @NotNull
    public static Collection<String> splitTokens(@NotNull String expr) {
        expr = expr.trim();
        // So the first char mustn't be a whitespace
        if (expr.length() == 0) throw new ExprTokenSplitException(expr + " is empty.");
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder b = new StringBuilder();
        // Peeks the first char
        char firstChar = expr.charAt(0);
        TokenType firstType = matchTokenType(firstChar);
        TokenState mode = switchState(firstType);
        LinkedList<Character> stacks = asStack(expr);
        while (!stacks.isEmpty()) {
            char c = stacks.pop();
            TokenType cur = matchTokenType(c);
            switch (mode) {
                case Digits: {
                    if (cur == Digit) {
                        // Digits mode only allows Digit to follow.
                        b.append(c);
                    } else {
                        // Otherwise, push it and switch state
                        composeTemp(tokens, b);
                        mode = switchState(cur);
                        stacks.push(c);
                    }
                }
                break;
                case Operator: {
                    if (cur == OperatorPart) {
                        b.append(c);
                    } else {
                        // Otherwise, it indicates the operator is over so let's check it.
                        String op = b.toString();
                        if (op.length() > MaxOpLen)
                            throw new ExprTokenSplitException("An operator is over than max length " + MaxOpLen + " in" + expr);
                        if (checkOperator(op)) {
                            composeTemp(tokens, b);
                        }
                        mode = switchState(cur);
                        stacks.push(c);
                    }
                }
                break;
                case Reference: {
                    if (cur == ReferenceSymbol || cur == Else || cur == Digit) {
                        // Reference mode allows @, char or digit
                        b.append(c);
                    } else {
                        // Otherwise, push it and switch state
                        composeTemp(tokens, b);
                        mode = switchState(cur);
                        stacks.push(c);
                    }
                }
                break;
                case Chars: {
                    if (cur == Else || cur == Digit) {
                        // String mode allows a digit or an else one but doesn't append "
                        if (c != StringIndicator)
                            b.append(c);
                    } else {
                        // Otherwise, push it and switch state
                        composeTemp(tokens, b);
                        mode = switchState(cur);
                        stacks.push(c);
                    }
                }
                break;
                case JustConsume: {
                    if (c != ' ')
                        b.append(c);
                    composeTemp(tokens, b);
                    Character next = stacks.peek();
                    if (next != null) {
                        mode = switchState(matchTokenType(next));
                    }
                }
            }
        }
        composeTemp(tokens, b);
        return tokens;
    }

    private static void composeTemp(
            @NotNull ArrayList<String> list,
            @NotNull StringBuilder b) {
        if (b.length() > 0) {
            list.add(b.toString());
            b.setLength(0);
        }
    }

    /**
     * Gets the next state by current type
     *
     * @param type can't be whitespace
     * @return the next state
     */
    private static TokenState switchState(TokenType type) {
        switch (type) {
            case Digit:
                return Digits;
            case OperatorPart:
                return Operator;
            case ReferenceSymbol:
                return Reference;
            case Parenthesis:
            case Whitespace:
                return JustConsume;
            case Else:
            default:
                return Chars;
        }
    }

    @NotNull
    private static TokenType matchTokenType(char c) {
        if (c == ' ')
            return Whitespace;
        else if (c == ReferenceIndicator)
            return ReferenceSymbol;
        else if (c == LeftPar || c == RightPar)
            return Parenthesis;
        else if (c >= '0' && c <= '9')
            return Digit;
        else if (OperatorIndicators.contains(c))
            return OperatorPart;
        else
            return Else;
    }

    private static LinkedList<Character> asStack(String str) {
        LinkedList<Character> res = new LinkedList<>();
        for (char c : str.toCharArray()) {
            res.add(c);
        }
        return res;
    }

    private static boolean checkOperator(String op) {
        return Operators.contains(op);
    }

    public enum TokenState {
        Digits, Operator, Reference, Chars, JustConsume
    }

    public enum TokenType {
        Whitespace, Digit, OperatorPart, Parenthesis, ReferenceSymbol, Else
    }

    public static boolean isTrue(Object obj) {
        if (obj instanceof Boolean)
            return (boolean) obj;
        else if (obj instanceof String)
            return !((String) obj).isEmpty();
        else if (obj instanceof Array)
            return Array.getLength(obj) > 0;
        else if (obj instanceof Collection)
            return !((Collection<?>) obj).isEmpty();
        else
            return Objects.equals(obj, 1);
    }
}
