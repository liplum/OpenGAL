package opengal.syntax;

import opengal.core.StoryTree;
import opengal.syntax.keywords.ElseKw;
import opengal.syntax.keywords.EntryKw;
import opengal.syntax.keywords.IfKw;
import opengal.tree.Node;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

public class Analyzer implements IAnalyzer {
    public static final char DELIMITER = ' ', NEWLINE = '\n';

    public static final HashSet<Character> SYMBOL_TOKEN = new HashSet<>(
        Arrays.asList(
            ',', '.', '+', '-', '*', '/', '%',
            '<', '>', '[', ']', '(', ')', '{', '}',
            '\\', '|', '?', '$', '!', '&', '~', '#', '^'
        )
    );

    public static final LinkedHashMap<Class<? extends Keyword>, Keyword> keywords = new LinkedHashMap<>();

    public AnalysisContext context;

    public void assignKeywords(){
        keywords.put(IfKw.class, new IfKw());
        keywords.put(ElseKw.class, new ElseKw());
        keywords.put(EntryKw.class, new EntryKw());
    }

    public Keyword match(String token){
        for(Keyword value: keywords.values()){
            if(value.headMatcher(token)) return value;
        }
        return null;
    }

    @Override
    @NotNull
    public StoryTree analyze(@NotNull Reader reader) {
        return null;
    }

    @NotNull
    public StoryTree analyze(@NotNull String code) {
        StringReader reader = new StringReader(code);
        int c;
        try{
            StringBuilder curr = new StringBuilder();
            Keyword currKw = null;
            boolean space = false;

            while((c = reader.read()) != -1){
                char character = Character.toString((char)c).charAt(0);

                if(character != DELIMITER) space = false;

                if(character == DELIMITER || character == NEWLINE || SYMBOL_TOKEN.contains(character)){
                    if(character == DELIMITER){
                        if(space) continue;
                        space = true;
                    }

                    if(currKw == null){
                        currKw = match(curr.toString());
                    }
                    if(currKw != null) currKw.read(curr.toString());
                    curr = new StringBuilder();

                    if(character == NEWLINE){
                        if(currKw != null){
                            currKw.check(context);
                            currKw.product(context);
                            currKw.finish();
                        }
                        currKw = null;
                    }

                    if(SYMBOL_TOKEN.contains(character)){
                        if(currKw != null){
                            currKw.read(String.valueOf(character));
                        }
                    }

                    continue;
                }

                curr.append(character);
            }
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }

        return new StoryTree(context.allNodes);
    }

    @Override
    @NotNull
    public String generate(@NotNull StoryTree tree) {
        return "";
    }
}
