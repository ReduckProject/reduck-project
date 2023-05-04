package net.reduck.chat.server.environment;

import lombok.Data;
import net.reduck.chat.server.environment.scanner.EndpointMappingScanner;
import net.reduck.chat.server.environment.scanner.EndpointScanner;
import net.reduck.chat.server.handler.AnnotationHandler;
import net.reduck.chat.server.handler.EndpointHandler;
import net.reduck.chat.server.handler.EndpointMappingDescriptor;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.chat.server.pojo.LoginTO;
import net.reduck.chat.server.reslover.AnnotationResolver;
import net.reduck.validator.JsonUtils;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Reduck
 * @since 2023/1/9 16:43
 */
@Data
public class ApplicationEnvironment {
    private final Reflections reflections;

    public ApplicationEnvironment(String packageName) {
        reflections = new Reflections(packageName);
    }

    public ApplicationEnvironment(String... packageName) {
        if(packageName == null || packageName.length == 0) {
            reflections = new Reflections();
        }else {
            reflections = new Reflections((Object[]) packageName);
        }
    }

    private Map<String, EndpointMappingDescriptor> mappingDescriptorMap;
    private Map<Class<? extends Annotation>, AnnotationHandler> handlerMap;

    private Map<Class<? extends Annotation>, AnnotationResolver> resolverMap;

    private Map<Class<?>, Object> endpointInstanceMap;

    public static void init(String... packageName) throws InstantiationException, IllegalAccessException {
        ApplicationEnvironment environment = new ApplicationEnvironment(packageName);

        EndpointScanner scanner = new EndpointScanner(environment.getReflections());
        Set<Class<?>> endpointSet = scanner.scan();

        // init EndpointMappingDescriptor
        EndpointMappingScanner endpointMappingScanner = new EndpointMappingScanner(endpointSet);
        List<EndpointMappingDescriptor> endpointMappingDescriptors = endpointMappingScanner.scan();
        environment.setMappingDescriptorMap(endpointMappingDescriptors
                .stream()
                .collect(Collectors.toMap(EndpointMappingDescriptor::getCanonicalPath, endpointMappingDescriptor -> endpointMappingDescriptor)));

        // init endpoint instance
        Map<Class<?>, Object> endpointInstanceMap = new HashMap<>();
        for (Class<?> endpointClass : endpointSet) {
            endpointInstanceMap.put(endpointClass, endpointClass.newInstance());
        }
        environment.setEndpointInstanceMap(endpointInstanceMap);

        // init annotation resolver
        Set<Class<? extends AnnotationResolver>> annotationResolverClassSet = environment.getReflections().getSubTypesOf(AnnotationResolver.class);
        Map<Class<? extends Annotation>, AnnotationResolver> annotationResolverMap = new HashMap<>();
        for (Class<? extends AnnotationResolver> resolverType : annotationResolverClassSet) {
            AnnotationResolver instance = resolverType.newInstance();
            annotationResolverMap.put(instance.getSupportAnnotation(), resolverType.newInstance());
        }
        environment.setResolverMap(annotationResolverMap);

        // init handler
        Set<Class<? extends AnnotationHandler>> annotationHanlderTypeSet = environment.getReflections().getSubTypesOf(AnnotationHandler.class);
        Map<Class<? extends Annotation>, AnnotationHandler> annotationHandlerMap = new HashMap<>();
        for (Class<? extends AnnotationHandler> handlerType : annotationHanlderTypeSet) {
            AnnotationHandler instance = handlerType.newInstance();
            annotationHandlerMap.put(instance.getSupportAnnotation(), handlerType.newInstance());
        }
        environment.setHandlerMap(annotationHandlerMap);

        ApplicationEnvironmentHolder.update(environment);
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        init("net.reduck");
        EndpointHandler handler = new EndpointHandler();
        ChatRequest<String> request = new ChatRequest<>();
        request.setData(JsonUtils.object2Json(new LoginTO()));
        request.setEndpoint("/login");
        request.setCookie("test");
        Object response = handler.handle(request);

        System.out.println(response.toString());
    }

}
