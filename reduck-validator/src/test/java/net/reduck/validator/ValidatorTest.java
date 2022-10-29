package net.reduck.validator;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author Gin
 * @since 2022/10/27 10:02
 */
public class ValidatorTest {

    @Test
    public void testValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TestUser>> v = validator.validate(new TestUser());

        for(ConstraintViolation<TestUser> constraintViolation : v){
            System.out.println(constraintViolation.getMessage());
            System.out.println(constraintViolation.toString());
        }
    }
}
