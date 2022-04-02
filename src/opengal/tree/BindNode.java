package opengal.tree;

import opengal.core.IInterpreter;

public class BindNode implements Node{
    public String boundName;

    @Override
    public void operate(IInterpreter in) {
        in.bind(boundName);
    }
}
