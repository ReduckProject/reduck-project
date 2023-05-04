package net.reduck.api.doc.descriptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gin
 * @since 2023/4/25 11:17
 */
public class ParameterInfo {

    private String name;

    private List<Annotation> annotations = new ArrayList<>();

    private String type;

    private String description;

    private String constraints;
}
