package net.reduck.data.protection;

import net.reduck.data.protection.core.DataProtector;
import net.reduck.data.protection.core.ProtectionPassword;
import net.reduck.data.protection.core.protection.EmptyDataProtector;
import net.reduck.data.protection.core.protection.PasswordProtector;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Reduck
 * @since 2022/9/2 10:55
 */
public class DataProtectionFactory {
    private final Map<Class<? extends Annotation>, DataProtector> dataProtectorMap = new HashMap<>();

    public DataProtectionFactory() {
        dataProtectorMap.put(ProtectionPassword.class, new PasswordProtector());
    }

    public void register(Class<? extends Annotation> annotation, DataProtector dataProtector) {
        dataProtectorMap.put(annotation, dataProtector);
    }

    public DataProtector getDataProtector(Class<? extends Annotation> annotation) {
        return dataProtectorMap.getOrDefault(annotation, EmptyDataProtector.getInstance());
    }
}
