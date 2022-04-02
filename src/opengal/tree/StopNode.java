package opengal.tree;

import opengal.core.IInterpreter;

public class StopNode implements Node{
    public static final StopNode X = new StopNode();
    @Override
    public void operate(IInterpreter in) {
        in.terminate();
    }
}
