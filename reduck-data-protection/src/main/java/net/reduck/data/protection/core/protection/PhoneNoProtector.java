package net.reduck.data.protection.core.protection;

import net.reduck.data.protection.core.DataProtector;

/**
 * @author Reduck
 * @since 2022/9/2 15:12
 */
public class PhoneNoProtector implements DataProtector {
    @Override
    public <T> T handle(T value) {
        if (value instanceof String) {
            if (((String) value).length() == 11) {
                return (T) (((String) value).substring(0, 3) + "********");
            }
            return value;
        }

        return value;
    }

}
