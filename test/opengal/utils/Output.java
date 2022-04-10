package opengal.utils;

import java.util.Collection;

public class Output {
    public static String compose(Collection<?> objects) {
        return compose(objects, " ");
    }

    public static String compose(Collection<?> objects, String separator) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int len = objects.size();
        for (Object obj : objects) {
            sb.append(obj);
            if (i != len - 1)
                sb.append(separator);
            i++;
        }
        return sb.toString();
    }
}
