package opengal.tree;

import opengal.core.IInterpreter;

public class JumpNode implements Node {
    public int destination;

    @Override
    public void operate(IInterpreter in) {
        in.jumpTo(destination);
    }
}
