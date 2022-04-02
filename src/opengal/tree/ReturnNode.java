package opengal.tree;

import opengal.core.IInterpreter;

public class ReturnNode implements Node{
    @Override
    public void operate(IInterpreter in) {
        in.returnBlock();
    }
}
