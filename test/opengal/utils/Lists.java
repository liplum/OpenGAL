package opengal.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Lists {
    @SafeVarargs
    public static <E> List<E> of(E... elements) {
        if (elements.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(elements);
    }
}
