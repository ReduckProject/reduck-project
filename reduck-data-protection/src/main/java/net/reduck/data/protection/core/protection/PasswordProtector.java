package net.reduck.data.protection.core.protection;

import net.reduck.data.protection.core.DataProtector;

/**
 * @author Reduck
 * @since 2022/8/26 11:57
 */
public class PasswordProtector implements DataProtector {

    @Override
    public <T> T handle(T value) {
        if (value instanceof String) {
            return (T) "******";
        }
        return value;
    }
}
