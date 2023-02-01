package net.reduck.chat.server.handler;

import net.reduck.chat.server.annotation.EndpointBody;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.validator.JsonUtils;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

/**
 * @author Reduck
 * @since 2023/1/9 15:57
 */
public class EndpointBodyHandler implements AnnotationHandler {

    @Override
    public Object handle(EndpointArgumentDescriptor descriptor, ChatRequest<byte[]> chatRequest) {
        return JsonUtils.json2Object(chatRequest.getData(), descriptor.getParameterType());
    }

    @Override
    public Class<? extends Annotation> getSupportAnnotation() {
        return EndpointBody.class;
    }

    public static void main(String[] args) {
        EndpointBodyHandler bo= new EndpointBodyHandler();
        ServiceLoader<AnnotationHandler> serviceLoader = ServiceLoader.load(AnnotationHandler.class);
        for(AnnotationHandler handler : serviceLoader) {
            System.out.println(handler.getClass().getName());
        }

        System.out.println();
    }
}
