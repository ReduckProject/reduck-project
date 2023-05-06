package net.reduck.apt.processor;

import com.sun.source.util.TreePath;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import net.reduck.apt.annotation.Crypto;
import net.reduck.apt.annotation.CryptoConverter;
import net.reduck.apt.annotation.Test;

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
 * @since 2023/5/4 16:19
 */
@SupportedAnnotationTypes("net.reduck.apt.annotation.Crypto")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class CryptoProcessor extends AbstractProcessor {
    private Types types = null;
    private Elements elements = null;
    private Filer filer = null;
    private Messager messager = null;

    private static Map<String, String> existPackage = new ConcurrentHashMap<>();

    private JavacTrees trees;

    private TreeMaker treeMaker;

    private Names names;

    private Symtab symtab;

    private AstMojo astMojo;

    FileOutputStream os = new FileOutputStream(new File(System.getProperty("user.home") + "/Downloads/processor.log"), true);

    private String convertPackageName = CryptoConverter.class.getPackage().getName();
    private String convertName = CryptoConverter.class.getSimpleName();

    private Class convertClass = Crypto.class;
    private String convertFiledPackageName = Test.class.getPackage().getName();
    private String convertFiled = Test.class.getSimpleName();

    private final String cryptoFullName = Crypto.class.getName();

    private JCTree.JCAnnotation templateAnnotation;

    public CryptoProcessor() throws FileNotFoundException {
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 判断方法应该添加什么注解
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Crypto.class)) {

            javax.lang.model.element.Name methodName = element.getSimpleName();

            TypeElement typeElem = (TypeElement) element.getEnclosingElement();

            String typeName = typeElem.getQualifiedName().toString();
            astMojo.importIfAbsent(element, CryptoConverter.class);
            astMojo.importIfAbsent(element, Test.class);
//            if (!existPackage.containsKey(typeName)) {
//                TreePath path = trees.getPath(typeElem);
//                JCTree.JCIdent jcIdent = treeMaker.Ident(names.fromString(convertPackageName));
//                Name className = names.fromString(convertName);
//                JCTree.JCFieldAccess jcFieldAccess = treeMaker.Select(jcIdent, className);
//                JCTree.JCImport anImport = treeMaker.Import(jcFieldAccess, false);
//
//                // 导入注解类
//                TreePath path = trees.getPath(typeElem);
//                JCTree.JCCompilationUnit jccu = (JCTree.JCCompilationUnit) path.getCompilationUnit();
//                jccu.defs = jccu.defs.prepend(anImport);
//
//                JCTree.JCIdent jcIdent2 = treeMaker.Ident(names.fromString(convertFiledPackageName));
//                Name className2 = names.fromString(convertFiled);
//                JCTree.JCFieldAccess jcFieldAccess2 = treeMaker.Select(jcIdent2, className2);
//                JCTree.JCImport anImport2 = treeMaker.Import(jcFieldAccess2, false);
//                // 导入转化类
//                TreePath path2 = trees.getPath(typeElem);
//                JCTree.JCCompilationUnit jccu2 = (JCTree.JCCompilationUnit) path2.getCompilationUnit();
//                jccu2.defs = jccu2.defs.prepend(anImport2);
//
//                existPackage.put(typeName, "Exist");
//            }

            JCTree jcTree = trees.getTree(typeElem);

            jcTree.accept(new TreeTranslator() {

                @Override
                public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
                    log("field : " + jcVariableDecl.toString());
                    List<JCTree.JCAnnotation> jcAnnotations = jcVariableDecl.mods.annotations;

                    try {
                        if (jcAnnotations != null && jcAnnotations.size() > 0) {
                            List<JCTree.JCAnnotation> nil = List.nil();

                            for (JCTree.JCAnnotation jcAnnotation : jcAnnotations) {
                                if (Crypto.class.getName().equals(jcAnnotation.getAnnotationType().type.tsym.toString())) {
                                    log("append: ...");
                                    JCTree.JCAnnotation converterAnnotation = treeMaker.Annotation(
                                            select(CryptoConverter.class.getName()),
//                                            List.of(
//                                                    // convert() 默认为 Void.class
//                                                    treeMaker.Assign(
//                                                            treeMaker.Ident(names.fromString("convert")),
//                                                            select(Test.class.getName()))
//                                                    )
                                            List.nil()
                                    );

                                    converterAnnotation = treeMaker.Annotation(
                                            select(CryptoConverter.class.getName())
                                            ,List.of(treeMaker.Assign(treeMaker.Ident(names.fromString("convert"))
                                                            , treeMaker.Select(treeMaker.Ident(names.fromString(Test.class.getSimpleName()))
                                                            , names.fromString("class")))));
                                    // 构造注解参数
//                                    JCTree.JCExpression arg = treeMaker.Ident(names.fromString("Test.class"));
//                                    List<JCTree.JCExpression> args = List.of(arg);// 构造注解
//                                    JCTree.JCAnnotation annotation = treeMaker.Annotation(
//                                            treeMaker.Ident(names.fromString("CryptoConverter.class")),
//                                            args
//                                    );

                                    nil = nil.append(converterAnnotation);
                                    if (templateAnnotation != null) {
//                                        nil = nil.append(templateAnnotation);
                                        log(templateAnnotation.getTag()
                                                + "," + templateAnnotation.getKind()
                                                +  "," + templateAnnotation.getAnnotationType()
                                                +  "," + templateAnnotation.getStartPosition()
                                                +  "," + templateAnnotation.getPreferredPosition()
                                                +  "," + templateAnnotation.getArguments()
                                                +  "," + templateAnnotation.getTree()
                                                +  "," + templateAnnotation.getTree().type
                                                +  "," + templateAnnotation.getTree().pos
                                        );

//                                        converterAnnotation = annotation;
                                        log(converterAnnotation.getTag()
                                                + "," + converterAnnotation.getKind()
                                                +  "," + converterAnnotation.getAnnotationType()
                                                +  "," + converterAnnotation.getStartPosition()
                                                +  "," + converterAnnotation.getPreferredPosition()
                                                +  "," + converterAnnotation.getArguments()
                                                +  "," + converterAnnotation.getTree()
                                                +  "," + converterAnnotation.getTree().type
                                                +  "," + converterAnnotation.getTree().pos
                                        );
                                    }
                                    log("append:" + converterAnnotation.toString());
                                } else {
                                    if (CryptoConverter.class.getName().equals(jcAnnotation.getAnnotationType().type.tsym.toString())) {
                                        templateAnnotation = jcAnnotation;
                                    }
                                    nil = nil.append(jcAnnotation);
                                    log("append original:" + jcAnnotation.toString());
                                }

                                log("nil size :" + nil.size());
                            }

                            jcVariableDecl.mods.annotations = nil;
                            log("list size is :" + jcAnnotations.size());
                            log("list size is :" + jcVariableDecl.mods.annotations.size());
                        }
                    } catch (Exception e) {
                        log("error :" + e.getMessage());
                        log("error :" + e.toString());
                    }

                    try {
                        super.visitVarDef(jcVariableDecl);
                    } catch (Exception e) {
                        log("error :" + e.getMessage());
                        log("error :" + e.toString());
                    }

                }
            });
        }

        return true;
    }

    JCTree.JCExpression select(String path) {
        JCTree.JCExpression expression = null;
        int i = 0;
        for (String split : path.split("\\.")) {
            if (i == 0)
                expression = treeMaker.Ident(names.fromString(split));
            else {
                expression = treeMaker.Select(expression, names.fromString(split));
            }
            i++;
        }

        return expression;
    }

    JCTree.JCExpression select2(String path) {
        JCTree.JCExpression expression = null;
        int i = 0;
        for (String split : path.split("\\.")) {
            if (i == 0)
                expression = treeMaker.Ident(names.fromString(split));
            else {
                expression = treeMaker.Select(expression, names.fromString("class"));
            }
            i++;
        }

        return expression;
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
        this.symtab = Symtab.instance(context);

        this.astMojo = new AstMojo(trees, treeMaker, names, symtab);
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
