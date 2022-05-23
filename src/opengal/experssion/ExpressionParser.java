package opengal.experssion;

import opengal.exceptions.ExpressionException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * 将一组token或一段字符串翻译为表达式结构树的解析工具
 * 目前支持的符号类型包括：
 * <pre>
 * 数学运算  + - * / %
 * 逻辑运算  && || !
 * 比较运算  == != > >= < <=
 * 字符拼接  ..
 * 赋值符号  =
 * </pre>
 * 关于符号拓展尚未进行很好的实现，后续可能开放定义运算符
 *
 * <p>关于表达式，请参见{@link Expression}
 *
 * @author EBwilson
 **/
public class ExpressionParser implements IExpressionParser {

    private static final HashSet<String> BIN_OPERATOR = new HashSet<>(
            Arrays.asList(
                    "!=", ">=", "<=", "&&", "||", ".."
            )
    );

    private static final HashSet<Character> NUMBER = new HashSet<>(
            Arrays.asList(
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            )
    );

    ArrayList<String> tokens = new ArrayList<>();

    @SuppressWarnings("unchecked")
    HashSet<String>[] priorityMap = new HashSet[11];

    /**
     * 将传入的字符按规范分割为token，并将其用作初始化一个表达式解析器实例
     * <p>请参见{@link ExprUtils}内的工具方法<strong>splitTokens(String expr)</strong>
     *
     * @param expression 要被解析的表达式字符串
     * @return 一个用传入字符串构建的解析器实例
     * */
    public static ExpressionParser by(String expression) {
        return new ExpressionParser(ExprUtils.splitTokens(expression));
    }

    /**
     * 使用给出的tokens列表创建一个表达式分析器实例，tokens应当是按规范正确被分割的元素构成的顺序集合
     *
     * @param tokens 需要解析的tokens顺序集合
     * */
    public ExpressionParser(Collection<String> tokens) {
        this.tokens.addAll(tokens);

        String symbol;
        for (int i = 1; i < tokens.size(); i++) {
            if (this.tokens.get(i - 1).length() == 1 && this.tokens.get(i).length() == 1
                    && BIN_OPERATOR.contains(symbol = this.tokens.get(i - 1) + this.tokens.get(i))) {
                this.tokens.set(i - 1, symbol);
                this.tokens.remove(i--);
            }
        }

        setParseUnities();
    }

    /**分配所有运算符的优先级池，这里应当正确的进行优先级划分，若重写此方法应当仅添加运算符，不应改变运算符的优先级*/
    public void setParseUnities() {
        for (int i = 0; i < priorityMap.length; i++) {
            priorityMap[i] = new HashSet<>();
        }

        priorityMap[0].add("=");
        priorityMap[1].add("..");
        priorityMap[2].add("||");
        priorityMap[3].add("&&");
        priorityMap[5].add("==");
        priorityMap[5].add("!=");
        priorityMap[6].add(">");
        priorityMap[6].add("<");
        priorityMap[6].add(">=");
        priorityMap[6].add("<=");
        priorityMap[7].add("+");
        priorityMap[7].add("-");
        priorityMap[8].add("*");
        priorityMap[8].add("/");
        priorityMap[8].add("%");
        priorityMap[9].add("!");
        priorityMap[10].add("(");
        priorityMap[10].add(")");
    }

    /**
     * 解析定义的表达式，返回表达式树结构的根
     *
     * @see Expression
     * @return 表达式树的根表达式
     * */
    @Override
    @SuppressWarnings("unchecked")
    public <T> @NotNull Expression<T> parse() {
        ParseUnit root = new ParseUnit();
        root.isRoot = true;

        for (String token : tokens) {
            root.read(token);
        }

        return (Expression<T>) root.parse();
    }

    /**表达式解析的单元，表达式解析树的节点，通过读取token进行解析*/
    protected class ParseUnit {
        public int depth;
        public boolean isRoot;

        protected ArrayList<String> tokens = new ArrayList<>();
        protected ArrayList<Object> symbolQueue = new ArrayList<>();
        protected ArrayList<ParseUnit> units = new ArrayList<>();

        protected ParseUnit curr;
        protected float bracketCount = 0;

        /**读取一个token,并向下方的子单元进行迭代读取*/
        public void read(String token) {
            boolean mark = true;

            if (token.equals("(")) {//TODO: 括号也许不应该被这样处理，内联到方法内这似乎很愚蠢
                bracketCount++;
            } else if (token.equals(")")) {
                bracketCount--;
                if (bracketCount == 0 && depth < priorityMap.length) {
                    curr.read(token);
                    curr.tokens.add(token);
                    return;
                }
            }

            if (bracketCount > 0) {
                if (depth <= priorityMap.length) {
                    if (curr == null && depth < priorityMap.length) {
                        units.add(curr = new ParseUnit());
                        curr.depth = depth + 1;
                        symbolQueue.add(curr);
                    }
                    tokens.add(token);
                    if (curr != null) curr.read(token);
                }
                return;
            }

            for (int i = 0; i <= Math.min(depth, priorityMap.length - 1); i++) {
                if (priorityMap[i].contains(token) || token.equals("(") || token.equals(")")) {
                    mark = false;
                    break;
                }
            }
            if (mark) tokens.add(token);

            if (depth >= priorityMap.length) return;
            if (priorityMap[depth].contains(token)) symbolQueue.add(token);
            if (curr == null || priorityMap[depth].contains(token)) {
                units.add(curr = new ParseUnit());
                symbolQueue.add(curr);
                curr.depth = depth + 1;
            }
            curr.read(token);
        }

        /**进行向子单元迭代的分析行为，构建关联子表达式分支的表达式并将其返回*/
        @SuppressWarnings("unchecked")
        public Expression<?> parse() {
            Expression<?> result = null;
            if (symbolQueue.size() > 1) {
                for (int i = 0; i < symbolQueue.size(); i++) {
                    Object symbol = symbolQueue.get(i);

                    if (symbol instanceof String) {
                        switch ((String) symbol) {

                            case "+":
                                result = handleOpCode(result, i, 0);
                                break;
                            case "-":
                                result = handleOpCode(result, i, 1);
                                break;
                            case "*":
                                result = handleOpCode(result, i, 2);
                                break;
                            case "/":
                                result = handleOpCode(result, i, 3);
                                break;
                            case "%":
                                result = handleOpCode(result, i, 4);
                                break;

                            case "==":
                                result = handleCompareOpCode(result, i, 0);
                                break;
                            case "!=":
                                result = handleCompareOpCode(result, i, 1);
                                break;
                            case ">":
                                result = handleCompareOpCode(result, i, 2);
                                break;
                            case "<":
                                result = handleCompareOpCode(result, i, 3);

                            case ">=":
                                result = handleCompareOpCode(result, i, 4);
                                break;
                            case "<=":
                                result = handleCompareOpCode(result, i, 5);
                                break;

                            case "&&":
                                result = handleBoolOpCode(result, i, 0);
                                break;
                            case "||":
                                result = handleBoolOpCode(result, i, 1);
                                break;

                            case "..": {
                                StringMergeExpression expr = new StringMergeExpression();
                                result = result == null ? associate(i, (a, b) -> {
                                    expr.a = (Expression<Object>) a.parse();
                                    expr.b = (Expression<Object>) b.parse();
                                    return expr;
                                }) :
                                        associate(result, i, (e, u) -> {
                                            expr.a = (Expression<Object>) e;
                                            expr.b = (Expression<Object>) u.parse();
                                            return expr;
                                        });
                                break;
                            }

                            case "=": {
                                AssignExpression<Object> expr = new AssignExpression<>();
                                result = result == null ? associate(i, (a, b) -> {
                                    Expression<?> ident = a.parse();
                                    if (!(ident instanceof IdentExpression))
                                        throw new ExpressionException("require identify but is >" + a + "< =" + b);
                                    expr.ident = (IdentExpression<Object>) ident;
                                    expr.exp = (Expression<Object>) b.parse();
                                    return expr;
                                }) :
                                        associate(result, i, (e, u) -> {
                                            if (!(e instanceof IdentExpression))
                                                throw new ExpressionException("require identify but is >" + e + "< =" + u);
                                            expr.ident = (IdentExpression<Object>) e;
                                            expr.exp = (Expression<Object>) u.parse();
                                            return expr;
                                        });
                                break;
                            }

                            case "!":
                                result = associate(null, i, (ignored, u) -> {
                                    NorExpression expr = new NorExpression();
                                    expr.exp = (Expression<Boolean>) u.parse();
                                    return expr;
                                });
                                break;
                        }
                    }
                }
            } else {
                if (symbolQueue.size() == 1) {
                    if (depth >= priorityMap.length - 1) {
                        if (tokens.size() > 1 && !tokens.get(0).equals("("))
                            throw new ExpressionException("unexpected token at >" + this + "<");
                        if (tokens.size() == 0) throw new ExpressionException("unexpected token at ><");

                        String token = tokens.get(0);

                        if (token.equals("(")) {
                            ParseUnit u = new ParseUnit();
                            for (int i = 1; i < tokens.size() - 1; i++) {
                                u.read(tokens.get(i));
                            }
                            result = u.parse();
                        } else if (token.startsWith("@")) {
                            result = new IdentExpression<>();
                            ((IdentExpression<?>) result).key = token.substring(1);
                        } else {
                            boolean isNumber = true, sym = true;
                            for (char c : token.toCharArray()) {
                                if (!NUMBER.contains(c)) isNumber = sym && (c == '+' || c == '-');
                                if (!isNumber) break;
                                sym = false;
                            }

                            if (isNumber) {
                                result = new ConstantExpression<Integer>();
                                ((ConstantExpression<Integer>) result).value = Integer.valueOf(token);
                            } else if (token.startsWith("\"") && token.endsWith("\"")) {
                                result = new ConstantExpression<String>();
                                ((ConstantExpression<String>) result).value = token.substring(1, token.length() - 1);
                            } else if (token.equals("true")) {
                                result = new ConstantExpression<Boolean>();
                                ((ConstantExpression<Boolean>) result).value = true;
                            } else if (token.equals("false")) {
                                result = new ConstantExpression<Boolean>();
                                ((ConstantExpression<Boolean>) result).value = false;
                            } else {
                                result = new ConstantExpression<String>();
                                ((ConstantExpression<String>) result).value = token;
                            }
                        }
                    } else result = ((ParseUnit) symbolQueue.get(0)).parse();
                }
            }

            return depth == 0 && !isRoot ? new ParExpression<>(result) : result;
        }

        @SuppressWarnings("unchecked")
        private Expression<Boolean> handleBoolOpCode(Expression<?> result, int index, int opcode) {
            return result == null ?
                    associate(index, (a, b) -> {
                        BoolOperatorExpression expr = new BoolOperatorExpression();
                        expr.opCode = opcode;
                        expr.a = (Expression<Boolean>) a.parse();
                        expr.b = (Expression<Boolean>) b.parse();
                        return expr;
                    }) :
                    associate(result, index, (e, u) -> {
                        BoolOperatorExpression expr = new BoolOperatorExpression();
                        expr.opCode = opcode;
                        expr.a = (Expression<Boolean>) e;
                        expr.b = (Expression<Boolean>) u.parse();
                        return expr;
                    });
        }

        @SuppressWarnings("unchecked")
        private Expression<Boolean> handleCompareOpCode(Expression<?> result, int index, int opcode) {
            return result == null ?
                    associate(index, (a, b) -> {
                        CompareExpression expr = new CompareExpression();
                        expr.opCode = opcode;
                        expr.a = (Expression<Object>) a.parse();
                        expr.b = (Expression<Object>) b.parse();
                        return expr;
                    }) :
                    associate(result, index, (e, u) -> {
                        CompareExpression expr = new CompareExpression();
                        expr.opCode = opcode;
                        expr.a = (Expression<Object>) e;
                        expr.b = (Expression<Object>) u.parse();
                        return expr;
                    });
        }

        @SuppressWarnings("unchecked")
        private Expression<Integer> handleOpCode(Expression<?> result, int index, int opcode) {
            return result == null ?
                    associate(index, (a, b) -> {
                        OperatorExpression expr = new OperatorExpression();
                        expr.opCode = opcode;
                        expr.a = (Expression<Integer>) a.parse();
                        expr.b = (Expression<Integer>) b.parse();
                        return expr;
                    }) :
                    associate(result, index, (e, u) -> {
                        OperatorExpression expr = new OperatorExpression();
                        expr.opCode = opcode;
                        expr.a = (Expression<Integer>) e;
                        expr.b = (Expression<Integer>) u.parse();
                        return expr;
                    });
        }

        @SuppressWarnings("unchecked")
        public <T> Expression<T> associate(int symIndex, ParseAcceptor2 cons) {
            return (Expression<T>) cons.accept((ParseUnit) symbolQueue.get(symIndex - 1), (ParseUnit) symbolQueue.get(symIndex + 1));
        }

        @SuppressWarnings("unchecked")
        public <T> Expression<T> associate(Expression<?> expr, int symIndex, ParseAcceptor cons) {
            return (Expression<T>) cons.accept(expr, (ParseUnit) symbolQueue.get(symIndex + 1));
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (depth == 0 && !isRoot) builder.append("(");
            for (Object token : depth >= priorityMap.length ? tokens : symbolQueue) {
                builder.append(token);
            }
            if (depth == 0 && !isRoot) builder.append(")");
            return builder.toString();
        }
    }

    @FunctionalInterface
    protected interface ParseAcceptor2 {
        Expression<?> accept(ParseUnit a, ParseUnit b);
    }

    @FunctionalInterface
    protected interface ParseAcceptor {
        Expression<?> accept(Expression<?> a, ParseUnit b);
    }
}
