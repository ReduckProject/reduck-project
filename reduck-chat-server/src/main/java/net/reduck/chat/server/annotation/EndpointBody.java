package net.reduck.chat.server.annotation;

import java.lang.annotation.*;

/**
 * @author Reduck
 * @since 2023/1/9 14:20
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EndpointBody {
}
