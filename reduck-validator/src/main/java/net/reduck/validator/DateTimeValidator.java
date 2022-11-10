package net.reduck.validator;

import net.reduck.validator.annotation.DateTime;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Gin
 * @since 2022/11/9 17:19
 */
public class DateTimeValidator implements ConstraintValidator<DateTime, CharSequence> {
    private Pattern pattern = Pattern.compile(ValidatorConstants.DATE_TIME_YYYY_MM_DD_HH_MM_SS);

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return StringUtils.isEmpty(value) || pattern.matcher(value).matches();
    }
}
