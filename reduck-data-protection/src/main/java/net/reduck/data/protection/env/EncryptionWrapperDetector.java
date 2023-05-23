package net.reduck.data.protection.env;

/**
 * @author Gin
 * @since 2023/5/6 15:00
 */
public class EncryptionWrapperDetector {
    private final String prefix;

    private final String suffix;

    public EncryptionWrapperDetector(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public boolean detected(String property) {
        return property != null && property.startsWith(prefix) && property.endsWith(suffix);
    }

    public String wrapper(String property) {
        return prefix + property + suffix;
    }

    public String unWrapper(String property) {
        return property.substring(prefix.length(), property.length() - suffix.length());
    }
}
