package opengal.experssion;

import org.jetbrains.annotations.NotNull;

public interface IExpressionParser {
    @NotNull <T> Expression<T> parse();
}
