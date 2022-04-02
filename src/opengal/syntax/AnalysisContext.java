package opengal.syntax;

import opengal.tree.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class AnalysisContext {
    public int line;

    public File file;

    public List<Node> allNodes;

    /**
     * Keyword token is started with "
     */
    public List<Node> keywords;
    /**
     * Meta token is started with @
     */
    public List<String> metas;

    public Map<Keyword, List<Node>> keywordList;

    public Map<Keyword, Stack<Node>> keywordStack;

    public int countOf(Keyword keyword){
        return keywordList.containsKey(keyword)? keywordList.get(keyword).size(): 0;
    }

    public void putNode(Keyword keyword, Node node){
        allNodes.add(node);
        keywords.add(node);
        keywordList.computeIfAbsent(keyword, e -> new ArrayList<>()).add(node);
    }

    public void pushStack(Keyword keyword, Node node){
        keywordStack.computeIfAbsent(keyword, e -> new Stack<>()).push(node);
    }

    public Node popStack(Keyword keyword){
        return keywordStack.computeIfAbsent(keyword, e -> new Stack<>()).pop();
    }
}
