package net.reduck.data.protection.env;

import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Reduck
 * @since 2022/9/26 17:15
 */
public class EncryptablePropertySource extends PropertySource<Map<String, Object>> {
    private final Map<String, Object> map;

    public EncryptablePropertySource(String name, Map<String, Object> source) {
        super(name, source);
        this.map = source;
    }

    public EncryptablePropertySource(String name) {
        super(name);
        this.map = new HashMap<>();
    }

    @Override
    public Object getProperty(String name) {
        return map.get(name);
    }
}
