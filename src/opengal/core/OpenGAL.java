package opengal.core;

import opengal.nl.NodeLang;
import opengal.syntax.Analyzer;
import opengal.syntax.experssion.NothingExpression;

public class OpenGAL {
    public static final Object Nothing = new Object();
    public static final NothingExpression NothingExpr = new NothingExpression();
    public static final Compiler Default;
    static {
        Default = new Compiler();
        Default.analyzer = new Analyzer();
        Default.nodeLang = NodeLang.Default;
    }
}
