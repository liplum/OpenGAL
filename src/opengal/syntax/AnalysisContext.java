package opengal.syntax;

import opengal.tree.StoryNode;

import java.util.List;
import java.util.Map;

public class AnalysisContext {
    public List<String> keywords;
    public List<String> metas;
    public Map<String,Class<? extends StoryNode>> keyword2node;
}
