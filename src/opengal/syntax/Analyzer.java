package opengal.syntax;

import opengal.core.StoryTree;
import opengal.tree.StoryNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Analyzer implements IAnalyzer {
    public AnalysisContext context;

    @NotNull
    public StoryTree analyze(String code) {
        ArrayList<StoryNode> nodes = new ArrayList<>();
        HashMap<String, Integer> block2Pos = new HashMap<>();
        String[] lines = code.split("\\\\r?\\\\n");


        return new StoryTree(nodes, block2Pos);
    }

    @Override
    @NotNull
    public String generate(StoryTree tree) {
        return "";
    }
}
