package opengal.core;

import org.jetbrains.annotations.NotNull;

public interface IAnalyzer {
    @NotNull
    NodeTree analyze(@NotNull String code);
}
