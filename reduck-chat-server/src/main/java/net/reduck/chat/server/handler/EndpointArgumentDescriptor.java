package net.reduck.chat.server.handler;

import lombok.Data;

import java.lang.annotation.Annotation;

/**
 * @author Reduck
 * @since 2023/1/9 15:49
 */
@Data
public class EndpointArgumentDescriptor {

    private Annotation[] annotationType;

    private Class<?> parameterType;
}
