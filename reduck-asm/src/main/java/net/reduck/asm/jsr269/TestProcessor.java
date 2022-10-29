package net.reduck.asm.jsr269;

import com.sun.source.tree.Tree;

import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;
import net.reduck.asm.Logger;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author Reduck
 * @since 2022/8/31 09:54
 */
@SupportedAnnotationTypes("net.reduck.asm.jsr269.Jcp269")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class TestProcessor extends AbstractProcessor {

    private Logger logger = new Logger();
    /**
     * JavacTrees提供了待处理的抽象语法树
     * TreeMaker中了一些操作抽象语法树节点的方法
     * Names提供了创建标识符的方法
     */
    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        logger.log("\n\n\n*********");

        roundEnv.getElementsAnnotatedWith(Jcp269.class).stream()
                .map(element -> trees.getTree(element))
                .forEach(tree -> tree.accept(new TreeTranslator() {
                    @Override
                    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                        prependParamAnnotation(jcClassDecl);
                        super.visitClassDef(jcClassDecl);
                    }
                }));

        return true;
    }

    /**
     * 在DAO方法参数前边追加@Param注解
     */
    private void prependParamAnnotation(JCTree.JCClassDecl jcClassDecl) {
        logger.log("\n\n\n*********" + jcClassDecl.name);
        jcClassDecl.defs.stream()
                .filter(element -> element.getKind().equals(Tree.Kind.METHOD))
                .map(methodTree -> (JCTree.JCMethodDecl) methodTree)
                .forEach(methodTree -> {
                    methodTree.getParameters().forEach(parameter -> {
                        JCTree.JCAnnotation paramAnnotation = createParamAnnotation(parameter);
                        logger.log(parameter.getModifiers().annotations.toString());
                        parameter.getModifiers().annotations.append(paramAnnotation);
                    });
                });
    }

    /**
     * 创建@Param注解对应的语法树对象
     */
    private JCTree.JCAnnotation createParamAnnotation(JCTree.JCVariableDecl parameter) {
        JCTree.JCLiteral value = treeMaker.Literal(parameter.name.toString());
        logger.log(value.value.toString());
        logger.log(names.fromString("Param").table.names._class.toString());

        return treeMaker.Annotation(
                treeMaker.Ident(names.fromString("Param")),
                List.of(treeMaker.Assign(treeMaker.Ident(names.fromString("value"))
                        , value)));
    }

}


