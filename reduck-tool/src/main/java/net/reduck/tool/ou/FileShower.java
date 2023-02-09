package net.reduck.tool.ou;

import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Reduck
 * @since 2022/11/11 10:45
 */
public class FileShower {

    public FileTreeNode show(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        FileTreeNode virtualNode = new FileTreeNode();

        traversal(file, virtualNode);

        return virtualNode.getNodes().get(0);
    }

    public void traversal(File file, FileTreeNode parentNode) {
        if (file.isDirectory()) {
            FileTreeNode node = new FileTreeNode(file.getName(), file.getPath(), true);
            parentNode.getNodes().add(node);
            File[] fileList = file.listFiles();
            if (fileList == null) {
                return;
            }

            for (File subFile : fileList) {
                traversal(subFile, node);
            }
        }

        FileTreeNode node = new FileTreeNode(file.getName(), file.getPath(), false);
        parentNode.getNodes().add(node);
    }

    @Data
    public static class FileTreeNode {
        public FileTreeNode(String name, String path, boolean directory) {
            this.name = name;
            this.path = path;
            this.directory = directory;
        }

        public FileTreeNode() {
        }

        private String name;

        private String path;

        private boolean directory;

        private List<FileTreeNode> nodes = new ArrayList<>();

        public String toMd() {
            StringBuilder md = new StringBuilder();
            toMd(this, md, "* ");
            return md.toString();
        }

        private void toMd(FileTreeNode node, StringBuilder md, String prefix) {
            if (node == null) {
                return;
            }

            md.append(prefix).append(node.getName()).append("\n");
            for (FileTreeNode sub : node.getNodes()) {
                toMd(sub, md, "  " + prefix);
            }
        }
    }
}
