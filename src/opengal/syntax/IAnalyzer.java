package opengal.syntax;

import opengal.core.StoryTree;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

public interface IAnalyzer {
    @NotNull
    StoryTree analyze(@NotNull Reader reader);
    @NotNull
    StoryTree analyze(@NotNull String code);
    @NotNull
    String generate(@NotNull StoryTree tree);
}
