package net.reduck.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Reduck
 * @since 2023/1/4 10:54
 */
public class Validators {

    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> Set<ConstraintViolation<T>> validate(T data) {
        return validator.validate(data);
    }
}
