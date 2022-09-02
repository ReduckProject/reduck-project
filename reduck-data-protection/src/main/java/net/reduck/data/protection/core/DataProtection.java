package net.reduck.data.protection.core;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonMerge;

import java.lang.annotation.*;

/**
 * @author Reduck
 * @since 2022/8/26 11:43
 */
@JacksonAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@JsonMerge
@Inherited
public @interface DataProtection {
}
