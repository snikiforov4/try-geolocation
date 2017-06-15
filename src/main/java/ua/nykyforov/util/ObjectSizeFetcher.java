package ua.nykyforov.util;

import java.lang.instrument.Instrumentation;
import java.util.Objects;

/**
 * @author Serhii Nykyforov
 */
public class ObjectSizeFetcher {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return Objects.requireNonNull(instrumentation, "instrumentation")
                .getObjectSize(o);
    }
}
