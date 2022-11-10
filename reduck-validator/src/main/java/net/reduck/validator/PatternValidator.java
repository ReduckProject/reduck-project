package net.reduck.validator;

import net.reduck.validator.annotation.Pattern;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.PatternSyntaxException;

/**
 * @author Reduck
 */
public class PatternValidator implements ConstraintValidator<Pattern, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        try {
            java.util.regex.Pattern.compile(value.toString());
        } catch (PatternSyntaxException e) {
            return false;
        }

        return true;
    }
}
