package opengal.tree;

import opengal.core.IInterpreter;

import java.util.Map;

public class OptionEndNode implements Node{
    public Map<Integer, String> number2Destination;

    @Override
    public void operate(IInterpreter in) {
        String destination = number2Destination.get(in.getSelected());
        if (destination != null) {
            in.jumpTo(destination);
        }
    }
}
