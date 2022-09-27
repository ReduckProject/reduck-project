package net.reduck.data.protection.env;

import org.springframework.core.env.PropertySource;

/**
 * @author Reduck
 * @since 2022/9/26 17:55
 */
public class PropertySourceWrapper extends PropertySource {
    private final String prefix = "$ENC{";
    private final String suffix = "}";
    private final PropertySource originalPropertySource;

    public PropertySourceWrapper(PropertySource originalPropertySource) {
        super(originalPropertySource.getName(), originalPropertySource.getSource());
        this.originalPropertySource = originalPropertySource;
    }

    @Override
    public Object getProperty(String name) {
        if (originalPropertySource.containsProperty(name)) {
            Object value = originalPropertySource.getProperty(name);
            if (value != null) {
                String property = value.toString();
                if (property != null && property.startsWith(prefix) && property.endsWith(suffix)) {
                    return property.substring(prefix.length(), property.length() - suffix.length());
                }
            }
        }
        return originalPropertySource.getProperty(name);
    }
}
