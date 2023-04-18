package net.reduck.jpa.specification.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gin
 * @since 2023/2/9 20:29
 */
public class SelectParser {
    private final Map<Class<?>, String> cache = new ConcurrentHashMap<>();

    public void parse(Class<?> selectType) {

    }
}
