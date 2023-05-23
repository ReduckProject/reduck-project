package net.reduck.apt.processor;

import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
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

    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;
    private Symtab symtab;
    private AstMojo astMojo;

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
            JCTree jcTree = trees.getTree(typeElem);

            jcTree.accept(new TreeTranslator() {

                @Override
                public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
                    List<JCTree.JCAnnotation> jcAnnotations = jcVariableDecl.mods.annotations;
                    try {
                        if (jcAnnotations != null && jcAnnotations.size() > 0) {
                            List<JCTree.JCAnnotation> nil = List.nil();

                            for (JCTree.JCAnnotation jcAnnotation : jcAnnotations) {
                                if (Crypto.class.getName().equals(jcAnnotation.getAnnotationType().type.tsym.toString())) {
                                    JCTree.JCAnnotation converterAnnotation = treeMaker.Annotation(
                                            astMojo.select(CryptoConverter.class.getName())
                                            ,List.of(treeMaker.Assign(treeMaker.Ident(names.fromString("convert"))
                                                            , treeMaker.Select(treeMaker.Ident(names.fromString(Test.class.getSimpleName()))
                                                            , names.fromString("class")))));

                                    nil = nil.append(converterAnnotation);
                                } else {
                                    nil = nil.append(jcAnnotation);
                                }
                            }

                            jcVariableDecl.mods.annotations = nil;
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }

                    try {
                        super.visitVarDef(jcVariableDecl);
                    } catch (Exception e) {
                        System.err.println(e);
                    }

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
        this.symtab = Symtab.instance(context);

        this.astMojo = new AstMojo(trees, treeMaker, names, symtab);
    }
}
