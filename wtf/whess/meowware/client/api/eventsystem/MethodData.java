package wtf.whess.meowware.client.api.eventsystem;

import java.lang.reflect.Method;

/**
 * @author DarkMagician6
 *
 * @since 2014-07-07
 */
public final class MethodData {
    public final Object source;
    public final Method target;
    public final byte priority;

    public MethodData(Object source, Method target, byte priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }
}