package opengal.tree;

import opengal.core.IInterpreter;

public class BlockEntryNode implements Node {
    public int blockHead;

    @Override
    public void operate(IInterpreter in) {
        in.pushIndex();
        in.jumpTo(blockHead + 1);
    }
}
