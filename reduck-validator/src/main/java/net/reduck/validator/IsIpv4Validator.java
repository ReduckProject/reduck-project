package net.reduck.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Reduck
 */
public class IsIpv4Validator implements ConstraintValidator<IsIpv4, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        return value.toString().matches(PatternConstant.IP_V4);
    }
}
