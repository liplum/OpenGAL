package opengal.syntax;

import opengal.core.StoryTree;
import org.jetbrains.annotations.NotNull;

public interface IAnalyzer {
    @NotNull
    StoryTree analyze(String code);
    @NotNull
    String generate(StoryTree tree);
}
