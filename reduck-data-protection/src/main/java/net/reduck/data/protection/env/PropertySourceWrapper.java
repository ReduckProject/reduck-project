package net.reduck.data.protection.env;

import org.springframework.core.env.PropertySource;

/**
 * @author Reduck
 * @since 2022/9/26 17:55
 */
public class PropertySourceWrapper<T> extends PropertySource {
    private final String prefix = "$ENC{";
    private final String suffix = "}";

    private final Encryptor encryptor;

    private final PropertySource<T> originalPropertySource;
    private final EncryptionWrapperDetector detector;


    public PropertySourceWrapper(PropertySource<T> originalPropertySource, Encryptor encryptor, EncryptionWrapperDetector detector) {
        super(originalPropertySource.getName(), originalPropertySource.getSource());
        this.originalPropertySource = originalPropertySource;
        this.encryptor = encryptor;
        this.detector = detector;
    }

    @Override
    public Object getProperty(String name) {
        if (originalPropertySource.containsProperty(name)) {
            Object value = originalPropertySource.getProperty(name);
            if (value != null) {
                String property = value.toString();
                if (detector.detected(property)) {
                    return encryptor.decrypt(detector.unWrapper(property));
                }
            }
        }
        return originalPropertySource.getProperty(name);
    }
}
