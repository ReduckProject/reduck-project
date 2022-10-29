package net.reduck.asm.jsr269;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Reduck
 * @since 2022/9/2 18:08
 */
@SupportedAnnotationTypes("net.reduck.asm.jsr269.Builder")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class BuilderProcessor extends AbstractProcessor {

    /**
     *
     * @param annotations to be processed
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement typeElement : annotations) {
            // Elements to be processed
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(typeElement);
            // Collect annotated methods
            Map<Boolean, List<Element>> annotatedMethods
                    = annotatedElements.stream().collect(Collectors.partitioningBy(
                    element -> ((ExecutableType) element.asType()).getParameterTypes().size() == 1
                            && element.getSimpleName().toString().startsWith("set")));
            List<Element> setters = annotatedMethods.get(true);
            List<Element> otherMethods = annotatedMethods.get(false);
            // If the annotated method is not a getter method
            otherMethods.forEach(element ->
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "@Builder must be applied to a setXxx method "
                                    + "with a single argument", element));
            // Pretreatment
            Map<String, String> setterMap = setters.stream().collect(Collectors.toMap(
                    setter -> setter.getSimpleName().toString(),
                    setter -> ((ExecutableType) setter.asType())
                            .getParameterTypes().get(0).toString()
            ));
            String className = ((TypeElement) setters.get(0)
                    .getEnclosingElement()).getQualifiedName().toString();
            try {
                writeBuilderFile(className, setterMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Build Builder
     * @param className the parent class
     * @param setterMap to be deal
     * @throws IOException
     */
    private void writeBuilderFile(
            String className, Map<String, String> setterMap)
            throws IOException {
        String packageName = null;
        // get the package
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }
        // get the class name
        String simpleClassName = className.substring(lastDot + 1);
        // the builder class name
        String builderClassName = className + "Builder";
        // package + builder class
        String builderSimpleClassName = builderClassName
                .substring(lastDot + 1);

        // create the builder class
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

        // write to the builder class
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }
            out.print("public class ");
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();
            out.print("    private ");
            out.print(simpleClassName);
            out.print(" object = new ");
            out.print(simpleClassName);
            out.println("();");
            out.println();
            out.print("    public ");
            out.print(simpleClassName);
            out.println(" build() {");
            out.println("        return object;");
            out.println("    }");
            out.println();
            setterMap.forEach((methodName, argumentType) -> {
                out.print("    public ");
                out.print(builderSimpleClassName);
                out.print(" ");
                out.print(methodName);

                out.print("(");

                out.print(argumentType);
                out.println(" value) {");
                out.print("        object.");
                out.print(methodName);
                out.println("(value);");
                out.println("        return this;");
                out.println("    }");
                out.println();
            });
            out.println("}");
        }
    }
}
