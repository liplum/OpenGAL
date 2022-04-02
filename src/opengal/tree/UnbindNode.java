package opengal.tree;

import opengal.core.IInterpreter;

public class UnbindNode implements Node{
    @Override
    public void operate(IInterpreter in) {
        in.unbind();
    }
}
