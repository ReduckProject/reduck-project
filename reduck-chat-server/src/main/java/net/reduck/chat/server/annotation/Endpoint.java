package net.reduck.chat.server.annotation;

import java.lang.annotation.*;

/**
 * @author Reduck
 * @since 2023/1/9 14:12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Endpoint {
}
