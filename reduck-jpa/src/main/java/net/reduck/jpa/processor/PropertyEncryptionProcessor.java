package net.reduck.jpa.processor;

import com.sun.source.util.TreePath;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import net.reduck.jpa.entity.convert.PropertyEncryptionConvert;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gin
 * @since 2022/9/29 10:11
 */
@SupportedAnnotationTypes("net.reduck.jpa.processor.PropertyEncryption")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@SuppressWarnings("all")
public class PropertyEncryptionProcessor extends AbstractProcessor {
    private Types types = null;
    private Elements elements = null;
    private Filer filer = null;
    private Messager messager = null;

    private static Map<String, String> existPackage = new ConcurrentHashMap<>();

    private JavacTrees trees;

    private TreeMaker treeMaker;

    private Names names;

    FileOutputStream os = new FileOutputStream(new File("/Users/zhanjinkai/Downloads/processor.log"), true);

    private String convertPackageName = "javax.persistence";
    private String convertName = "Convert";

    private Class convertClass = PropertyEncryptionConvert.class;
    private String convertFiledPackageName = "net.reduck.jpa.entity.convert";
    private String convertFiled = "PropertyEncryptionConvert";

    public PropertyEncryptionProcessor() throws FileNotFoundException {
        File file = new File("/Users/zhanjinkai/Downloads/processor.log");
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        messager.printMessage(Diagnostic.Kind.NOTE, "\n\n\n****************test----------\n\n\n");
//        // // 遍历所有被注解了的元素
//        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(PropertyEncryption.class)) {
//            log("Anotation is " + annotatedElement.toString());
//            if (annotatedElement.getKind() != ElementKind.FIELD) {
//                processingEnv.getFiler().
//                return true;
//            }
//        }
//
//        return true;
//    }

//    public boolean process(Set<? extends TypeElement> annotations,
//                           RoundEnvironment env) {
//        final Messager messager = processingEnv.getMessager();
//
//        // A processor must gracefully handle an empty set of annotations.
//        if (annotations.size() == 0) {
//            return false;
//        }
//
//        assert annotations.size() == 1 : "This processor expects only one annotation";
//
//        for (TypeElement annotationElement : annotations) {
//            final Set<? extends Element> annotatedElements = env.getElementsAnnotatedWith(annotationElement);
//            for (Element annotatedElement : annotatedElements) {
//
//                // Generate a Java source
//                try {
//                    final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(CLASS_NAME);
//                    final Writer sourceWriter = sourceFile.openWriter();
//                    sourceWriter.write("");
//                    sourceWriter.close();
//                } catch (Exception ex) {
//                    messager.printMessage(ERROR, "Error writing source" + ex.getMessage());
//                }
//
//                // Generate a Java class
//                try {
//                    final JavaFileObject classFile = processingEnv.getFiler().createClassFile(CLASS_NAME + "2");
//                    final OutputStream classStream = classFile.openOutputStream();
//                    ClassPool pool = ClassPool.getDefault();
//                    CtClass cc = pool.makeClass(CLASS_NAME + "2");
//                    classStream.write(cc.toBytecode());
//                    classStream.close();
//                } catch (Exception ex) {
//                    messager.printMessage(ERROR, "Error writing class" + ex.getMessage());
//                }
//            }
//        }
//
//        return true;
//    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 判断方法应该添加什么注解
        for (Element element : roundEnvironment.getElementsAnnotatedWith(PropertyEncryption.class)) {

            javax.lang.model.element.Name methodName = element.getSimpleName();

            TypeElement typeElem = (TypeElement) element.getEnclosingElement();

            String typeName = typeElem.getQualifiedName().toString();

            if (!existPackage.containsKey(typeName)) {
                JCTree.JCIdent jcIdent = treeMaker.Ident(names.fromString(convertPackageName));
                Name className = names.fromString(convertName);
                JCTree.JCFieldAccess jcFieldAccess = treeMaker.Select(jcIdent, className);
                JCTree.JCImport anImport = treeMaker.Import(jcFieldAccess, false);

                // 导入注解类
                TreePath path = trees.getPath(typeElem);
                JCTree.JCCompilationUnit jccu = (JCTree.JCCompilationUnit) path.getCompilationUnit();
                jccu.defs = jccu.defs.prepend(anImport);

                JCTree.JCIdent jcIdent2 = treeMaker.Ident(names.fromString(convertFiledPackageName));
                Name className2 = names.fromString(convertFiled);
                JCTree.JCFieldAccess jcFieldAccess2 = treeMaker.Select(jcIdent2, className2);
                JCTree.JCImport anImport2 = treeMaker.Import(jcFieldAccess2, false);
                // 导入转化类
                TreePath path2 = trees.getPath(typeElem);
                JCTree.JCCompilationUnit jccu2 = (JCTree.JCCompilationUnit) path2.getCompilationUnit();
                jccu2.defs = jccu2.defs.prepend(anImport2);

                existPackage.put(typeName, "Exist");
            }

            JCTree jcTree = trees.getTree(typeElem);

            jcTree.accept(new TreeTranslator() {

                @Override
                public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
                    log("filed : " + jcVariableDecl.toString());
                    String fieldName = jcVariableDecl.toString();
                    if (fieldName.contains("@" + PropertyEncryption.class.getSimpleName())) {
                        for (JCTree.JCAnnotation annotation : jcVariableDecl.mods.annotations) {
                            log(annotation.annotationType.type.toString());
                        }
                        log("this : " + jcVariableDecl.toString());

                        List<JCTree.JCAnnotation> annotations = jcVariableDecl.mods.annotations;
                        List<JCTree.JCAnnotation> nil = List.nil();
                        for (int i = 0; i < annotations.size(); i++) {
                            JCTree.JCAnnotation anno = annotations.get(i);
                            log("type is " + anno.type.toString());
//                            if (PropertyEncryption.class.getName().equals(anno.type.toString())) {
//                                JCTree.JCAnnotation e;
//                                e = treeMaker.Annotation(treeMaker.Ident(names.fromString(convertName)), // 注解名称
//                                        List.of(
//                                                treeMaker.Exec(
//                                                        treeMaker.Assign(treeMaker.Ident(names.fromString("converter")), // 注解属性
//                                                                treeMaker.Literal(TypeTag.TYPEVAR, convertFiled + ".class"))).expr)
//                                );// 注解属性值
//                                nil = nil.append(e);
//                            } else {
//                                nil = nil.append(anno);
//                            }
                        }
                        // 修改方法注解
                        jcVariableDecl.mods.annotations = nil;
                    }
                    super.visitVarDef(jcVariableDecl);
                }
            });
        }

        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.types = processingEnv.getTypeUtils();
        this.elements = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();

        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    private void log(String message) {
        try {
            os.write("\n".getBytes());
            os.write((format.format(System.currentTimeMillis()) + " " + message).getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
}
