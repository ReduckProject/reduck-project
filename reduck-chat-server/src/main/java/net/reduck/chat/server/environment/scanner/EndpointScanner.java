package net.reduck.chat.server.environment.scanner;

import net.reduck.chat.server.annotation.Endpoint;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @author Reduck
 * @since 2023/1/10 10:28
 */
public class EndpointScanner {
    private final Reflections reflections;

    public EndpointScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public Set<Class<?>> scan() {
        return reflections.getTypesAnnotatedWith(Endpoint.class);
    }

}
