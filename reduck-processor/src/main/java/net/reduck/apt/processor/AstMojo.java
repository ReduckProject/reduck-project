package net.reduck.apt.processor;

import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.lang.model.element.Element;

/**
 * @author Gin
 * @since 2023/5/6 09:58
 */
public class AstMojo {

    private final JavacTrees trees;

    private final TreeMaker treeMaker;

    private final Names names;

    private final Symtab symtab;

    public AstMojo(JavacTrees trees, TreeMaker treeMaker, Names names, Symtab symtab) {
        this.trees = trees;
        this.treeMaker = treeMaker;
        this.names = names;
        this.symtab = symtab;
    }

    public void importIfAbsent(Element element, Class<?> importType) {
        JCTree.JCCompilationUnit compilationUnit = ((JCTree.JCCompilationUnit) trees.getPath(element).getCompilationUnit());
        boolean contains = false;
        for (JCTree.JCImport jcImport : compilationUnit.getImports()) {
            if (importType.getName().equals(jcImport.getQualifiedIdentifier().toString())) {
                contains = true;
                break;
            }
        }

        if(!contains) {
            JCTree.JCIdent jcIdent = treeMaker.Ident(names.fromString(importType.getPackage().getName()));
            Name className = names.fromString(importType.getSimpleName());
            JCTree.JCFieldAccess jcFieldAccess = treeMaker.Select(jcIdent, className);
            JCTree.JCImport jcImport = treeMaker.Import(jcFieldAccess, false);
            compilationUnit.defs = compilationUnit.defs.prepend(jcImport);
        }
    }

    public JCTree.JCExpression select(String path) {
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

    public JCTree.JCIdent identFromString(String name) {
        return treeMaker.Ident(names.fromString(name));
    }
}
