package net.reduck.api.doc.util;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import com.thoughtworks.qdox.model.expression.Constant;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Reduck
 * @since 2022/12/12 09:24
 */
public class MappingHelper {
    private static Set<String> controllerClassNameSet = new HashSet<>();
    private static Set<String> requestMappingClassNameSet = new HashSet<>();

    static {
        controllerClassNameSet.add("org.springframework.stereotype.Controller");
        controllerClassNameSet.add("org.springframework.web.bind.annotation.RestController");

        requestMappingClassNameSet.add("org.springframework.web.bind.annotation.RequestMapping");
        requestMappingClassNameSet.add("org.springframework.web.bind.annotation.GetMapping");
        requestMappingClassNameSet.add("org.springframework.web.bind.annotation.PostMapping");
        requestMappingClassNameSet.add("org.springframework.web.bind.annotation.DeleteMapping");
        requestMappingClassNameSet.add("org.springframework.web.bind.annotation.PatchMapping");
        requestMappingClassNameSet.add("org.springframework.web.bind.annotation.PutMapping");
    }

    public static boolean containController(JavaClass javaClass) {
        for (JavaAnnotation javaAnnotation : javaClass.getAnnotations()) {
            if (controllerClassNameSet.contains(javaAnnotation.getType().getCanonicalName())) {
                return true;
            }
        }

        return false;
    }

    public static boolean containRequestMapping(JavaMethod javaMethod) {
        for (JavaAnnotation javaAnnotation : javaMethod.getAnnotations()) {
            if (requestMappingClassNameSet.contains(javaAnnotation.getType().getCanonicalName())) {
                return true;
            }
        }

        return false;
    }

    public static boolean containRequestMapping(JavaClass javaClass) {
        for (JavaAnnotation javaAnnotation : javaClass.getAnnotations()) {
            if (requestMappingClassNameSet.contains(javaAnnotation.getType().getCanonicalName())) {
                return true;
            }
        }

        return false;
    }

    public static String getUrl(Collection<JavaAnnotation> annotations) {
        if (CollectionUtils.isEmpty(annotations)) {
            return "";
        }

        String url = "";
        for (JavaAnnotation javaAnnotation : annotations) {
            if (!requestMappingClassNameSet.contains(javaAnnotation.getType().getCanonicalName())) {
                continue;
            }
            url = mappingNormalize(javaAnnotation);
            if (!StringUtils.isEmpty(url)) {
                return url;
            }
        }
        return "";
    }

    public static String mappingNormalize(JavaAnnotation javaAnnotation) {
        if (javaAnnotation == null || !requestMappingClassNameSet.contains(javaAnnotation.getType().getCanonicalName())) {
            throw new IllegalArgumentException("java class must in " + requestMappingClassNameSet.toString());
        }

        String url = mappingNormalize(javaAnnotation.getProperty("value"));
        return !StringUtils.isEmpty(url) ? url : mappingNormalize(javaAnnotation.getProperty("path"));
    }

    public static String mappingNormalize(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }

        url = url.startsWith("/") ? url : "/" + url;
        url = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;

        return url;
    }

    public static String mappingNormalize(AnnotationValue value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Constant) {
            if (((Constant) value).getValue() != null) {
                return mappingNormalize(((Constant) value).getValue().toString());
            }
        }

        return mappingNormalize(value.toString());
    }

    public static boolean containAnnotation(List<JavaAnnotation> annotationList, Class<?> annotationType) {
        for (JavaAnnotation annotation : annotationList) {
            if (annotation.getType().getCanonicalName().equals(annotationType.getCanonicalName())) {
                return true;
            }
        }

        return false;
    }
}
