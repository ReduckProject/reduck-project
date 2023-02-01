package net.reduck.chat.server.annotation;

import java.lang.annotation.*;

/**
 * @author Reduck
 * @since 2023/1/9 14:15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EndpointMapping {

    String value();
}
