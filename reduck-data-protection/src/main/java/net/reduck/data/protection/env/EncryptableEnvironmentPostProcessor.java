package net.reduck.data.protection.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * @author Reduck
 * @since 2022/9/26 16:46
 */
public class EncryptableEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private final String prefix = "$ENC{";
    private final String suffix = "}";

//    // 是否考虑创建一个代理，实现
//    @Override
//    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
//        Map<String, Object> encMap = new HashMap<>();
//
//        MutablePropertySources mutablePropertySources = environment.getPropertySources();
//        Iterator<PropertySource<?>> propertySourceIterator = mutablePropertySources.iterator();
//        while (propertySourceIterator.hasNext()) {
//            PropertySource propertySource = propertySourceIterator.next();
//            if (propertySource instanceof OriginTrackedMapPropertySource) {
//                Map<String, Object> map = ((OriginTrackedMapPropertySource) propertySource).getSource();
//                map.forEach((key, value) -> System.out.println(key + "=" + value + " | " + value.getClass()));
//
//                for (Map.Entry<String, Object> entry : map.entrySet()) {
//                    if (entry.getValue() != null) {
//                        String value = entry.getValue().toString();
//                        if (value == null) {
//                            continue;
//                        }
//                        if (value.startsWith(prefix) && value.endsWith(suffix)) {
//                            encMap.put(entry.getKey(), value.substring(prefix.length(), value.length() - suffix.length()));
//                        }
//                    }
//                }
//            }
//        }
//
//        if (encMap.size() > 0) {
//            mutablePropertySources.addFirst(new EncryptablePropertySource("enc_properties", encMap));
//        }
//    }


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources mutablePropertySources = environment.getPropertySources();

        for (PropertySource<?> propertySource : mutablePropertySources) {
            if (propertySource instanceof OriginTrackedMapPropertySource) {
                mutablePropertySources.addFirst(new PropertySourceWrapper(propertySource));
            }
        }
    }
}
