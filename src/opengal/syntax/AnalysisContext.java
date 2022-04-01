package opengal.syntax;

import opengal.tree.StoryNode;

import java.util.List;
import java.util.Map;

public class AnalysisContext {
    /**
     * Keyword token is started with "
     */
    public List<String> keywords;
    /**
     * Meta token is started with @
     */
    public List<String> metas;
    public Map<String,Class<? extends StoryNode>> keyword2node;
}
