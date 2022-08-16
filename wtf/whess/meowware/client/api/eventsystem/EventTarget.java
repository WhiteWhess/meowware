package wtf.whess.meowware.client.api.eventsystem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DarkMagician6
 *
 * @since 2014-07-07
 */
@Target(value={ElementType.METHOD})
@Retention(value= RetentionPolicy.RUNTIME)
public @interface EventTarget {
    public byte value() default 2;
}