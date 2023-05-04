package net.reduck.validator;

import net.reduck.validator.annotation.CollectionDistinct;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Reduck
 */
public class CollectionDistinctHelper implements ConstraintValidator<CollectionDistinct, Collection<Object>> {
    private boolean nullable;

    @Override
    public boolean isValid(Collection value, ConstraintValidatorContext context) {
        if (!CollectionUtils.isEmpty(value)) {
            Set<Object> set = new HashSet<>();
            if (!nullable) {
                set.add(null);
            }

            Iterator<?> iterator = value.iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (set.contains(o)) {
                    iterator.remove();
                } else {
                    set.add(o);
                }
            }
        }
        return true;
    }

    @Override
    public void initialize(CollectionDistinct constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
    }
}