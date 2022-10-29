package net.reduck.jpa.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gin
 * @since 2022/9/29 09:54
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
public @interface PropertyEncryption {
}
