package jux.test;

import java.lang.annotation.*;

/**
 * Marker annotation for the {@link JuxExtension} to inject the current port.
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestServerPort {
}
