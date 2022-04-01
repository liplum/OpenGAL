package opengal.api;

import org.jetbrains.annotations.NotNull;

public interface IAction {
    void invoke(@NotNull Object[] args);
}
