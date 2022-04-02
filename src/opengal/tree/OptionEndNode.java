package opengal.tree;

import opengal.core.IInterpreter;

import java.util.Map;

public class OptionEndNode implements Node {
    public Map<Integer, Integer> number2Destination;

    @Override
    public void operate(IInterpreter in) {
        int option = in.get("option");
        int destination = number2Destination.get(option);
        in.jumpTo(destination);
    }
}
