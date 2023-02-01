package net.reduck.chat.server.handler;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Reduck
 * @since 2023/1/9 15:45
 */
@Data
public class EndpointMappingDescriptor {
    private String canonicalPath;
    private Class<?> endpoint;

    private Method endpointMappingMethod;

    private EndpointArgumentDescriptor[] endpointArgumentDescriptors;
}
