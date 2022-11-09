package net.reduck.validator;

import net.reduck.validator.annotation.IsPattern;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Reduck
 */
public class IsPatternValidator implements ConstraintValidator<IsPattern, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        try {
            Pattern.compile(value.toString());
        } catch (PatternSyntaxException e) {
            return false;
        }

        return true;
    }
}
