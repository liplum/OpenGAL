package opengal.tree;

import opengal.core.IInterpreter;

public class BlockEndNode implements Node{
    public static final BlockEndNode X = new BlockEndNode();
    @Override
    public void operate(IInterpreter in) {
        in.popIndex();
    }
}
