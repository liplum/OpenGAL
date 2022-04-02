package opengal.tree;

import opengal.core.IInterpreter;

public class ReturnNode implements Node{
    public static final ReturnNode X = new ReturnNode();
    @Override
    public void operate(IInterpreter in) {
        in.rollbackPopIndex();
    }
}
