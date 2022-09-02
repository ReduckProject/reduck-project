package net.reduck.data.protection.core.protection;

import net.reduck.data.protection.core.DataProtector;

/**
 * @author Reduck
 * @since 2022/9/2 11:20
 */
public class EmptyDataProtector implements DataProtector {
    private final static EmptyDataProtector INSTANCE = new EmptyDataProtector();

    @Override
    public <T> T handle(T value) {
        return value;
    }

    public static EmptyDataProtector getInstance() {
        return INSTANCE;
    }
}
