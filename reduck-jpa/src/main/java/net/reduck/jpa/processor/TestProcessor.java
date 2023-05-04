//package net.reduck.jpa.processor;
//
//import com.sun.source.util.TreePath;
//import com.sun.tools.javac.api.JavacTrees;
//import com.sun.tools.javac.processing.JavacProcessingEnvironment;
//import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.tree.JCTree.JCAnnotation;
//import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
//import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
//import com.sun.tools.javac.tree.TreeMaker;
//import com.sun.tools.javac.tree.TreeTranslator;
//import com.sun.tools.javac.util.Context;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.util.Name;
//import com.sun.tools.javac.util.Names;
//
//import javax.annotation.processing.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@SupportedAnnotationTypes(TestProcessor.CLZ)
//public class TestProcessor extends AbstractProcessor {
//
//    public final static String CLZ = "net.reduck.jpa.processor.Hello";
//
//    private static Map<String, String> tis = new ConcurrentHashMap<>();
//
//    private JavacTrees trees;
//
//    private TreeMaker treeMaker;
//
//    private Names names;
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        this.trees = JavacTrees.instance(processingEnv);
//        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
//        this.treeMaker = TreeMaker.instance(context);
//        this.names = Names.instance(context);
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//
//        boolean thirdPresent = isPresent("com.xxl.job.core.handler.annotation.XxlJob");
//
//        // 判断方法应该添加什么注解
//        String pkg, clz;
//        if (thirdPresent) {
//            pkg = "com.xxl.job.core.handler.annotation";
//            clz = "XxlJob";
//        } else {
//            pkg = "org.springframework.scheduling.annotation";
//            clz = "Scheduled";
//        }
//
//        for (Element element : roundEnvironment.getElementsAnnotatedWith(Hello.class)) {
//
//            javax.lang.model.element.Name methodName = element.getSimpleName();
//
//            TypeElement typeElem = (TypeElement) element.getEnclosingElement();
//
//            String typeName = typeElem.getQualifiedName().toString();
//            String list = tis.get(typeName);
//
//
//            if (null == list) {
//                JCTree.JCIdent jcIdent = treeMaker.Ident(names.fromString(pkg));
//                Name className = names.fromString(clz);
//                JCTree.JCFieldAccess jcFieldAccess = treeMaker.Select(jcIdent, className);
//                JCTree.JCImport anImport = treeMaker.Import(jcFieldAccess, false);
//
//                // 导入注解类
//                TreePath path = trees.getPath(typeElem);
//                JCTree.JCCompilationUnit jccu = (JCCompilationUnit) path.getCompilationUnit();
//                jccu.defs = jccu.defs.prepend(anImport);
//
//                tis.put(typeName, "Scheduled");
//            }
//
//            JCTree jcTree = trees.getTree(typeElem);
//
//            jcTree.accept(new TreeTranslator() {
//
//                @Override
//                public void visitMethodDef(JCMethodDecl methodDecl) {
//                    if (methodName.toString().equals(methodDecl.getName().toString())) {
//                        if (methodDecl.mods.annotations.toString().contains("Hello")) {
//                            List<JCAnnotation> annotations = methodDecl.mods.annotations;
//                            List<JCAnnotation> nil = List.nil();
//                            for (int i = 0; i < annotations.size(); i++) {
//                                JCAnnotation anno = annotations.get(i);
//                                if (CLZ.equals(anno.type.toString())) {
//                                    JCAnnotation e;
//                                    if (thirdPresent) {
//                                        e = treeMaker.Annotation(treeMaker.Ident(names.fromString(clz)), // 注解名称
//                                                List.of(treeMaker.Exec(treeMaker.Assign(treeMaker.Ident(names.fromString("value")), // 注解属性
//                                                        treeMaker.Literal(methodName.toString()))).expr));// 注解属性值
//                                    } else {
//                                        e = treeMaker.Annotation(treeMaker.Ident(names.fromString(clz)), anno.args);
//                                    }
//                                    nil = nil.append(e);
//                                } else {
//                                    nil = nil.append(anno);
//                                }
//                            }
//                            // 修改方法注解
//                            methodDecl.mods.annotations = nil;
//                        }
//                    }
//                    super.visitMethodDef(methodDecl);
//                }
//            });
//        }
//        return false;
//    }
//
//    private static boolean isPresent(String name) {
//        try {
//            TestProcessor.class.getClassLoader().loadClass(name);
//            return true;
//        } catch (ClassNotFoundException e) {
//            return false;
//        }
//    }
//}