package net.reduck.jpa.entity.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Reduck
 * @since 2021/3/20 17:59
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
public @interface Crypto {

}
