package opengal.core;

import opengal.nl.NodeLang;
import opengal.syntax.Analyzer;
import opengal.syntax.experssion.NothingExpression;

public class OpenGAL {
    public static final Object Nothing = new Object();
    public static final NothingExpression NothingExpr = NothingExpression.X;
    public static final GalCompiler Default;
    static {
        Default = new GalCompiler();
        Default.analyzer = new Analyzer();
        Default.nodeLang = NodeLang.Default;
    }
}
