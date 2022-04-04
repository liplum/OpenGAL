package opengal.syntax;

import opengal.core.NodeTree;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

public interface IAnalyzer {
    @NotNull
    NodeTree analyze(@NotNull Reader reader);
    @NotNull
    NodeTree analyze(@NotNull String code);
    @NotNull
    String generate(@NotNull NodeTree tree);
}
