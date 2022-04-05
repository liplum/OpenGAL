package opengal.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.logging.Logger;

@SuppressWarnings("RedundantThrows")
public class Memory implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final Logger logger = Logger.getLogger(Memory.class.getName());
    private static final String START_FREE = "start free";

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        System.gc();
        getStore(context).put(START_FREE, Runtime.getRuntime().freeMemory());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        long startFree = getStore(context).remove(START_FREE, long.class);
        long endFree = Runtime.getRuntime().freeMemory();
        long spent = startFree - endFree;

        logger.info(() ->
                String.format(
                        "Method [%s] started with %s free and ended with %s free.\n It might spend %s."
                        , testMethod.getName(),
                        readableBytesSize(startFree),
                        readableBytesSize(endFree),
                        readableBytesSize(spent)
                ));
        System.gc();
    }


    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }

    public static String readableBytesSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
