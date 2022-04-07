package opengal.syntax;

import opengal.core.NodeTree;
import org.jetbrains.annotations.NotNull;

public interface IAnalyzer {
    @NotNull
    NodeTree analyze(@NotNull String code);
}
