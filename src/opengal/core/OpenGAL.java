package opengal.core;

import opengal.experssion.NothingExpression;
import opengal.nl.NodeLang;

public class OpenGAL {
    public static final Object Nothing = new Object();
    public static final int Version = 0;
    public static final NothingExpression NothingExpr = NothingExpression.X;
    public static final GalCompiler Default;

    static {
        Default = new GalCompiler();
        Default.nodeLang = NodeLang.Default;
    }
}
