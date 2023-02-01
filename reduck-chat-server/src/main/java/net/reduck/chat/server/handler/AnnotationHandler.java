package net.reduck.chat.server.handler;

import net.reduck.chat.server.pojo.ChatRequest;

import java.lang.annotation.Annotation;

/**
 * @author Reduck
 * @since 2023/1/9 15:57
 */
public interface AnnotationHandler {

    Object handle(EndpointArgumentDescriptor descriptor, ChatRequest<byte[]> chatRequest);

    Class<? extends Annotation> getSupportAnnotation();
}
