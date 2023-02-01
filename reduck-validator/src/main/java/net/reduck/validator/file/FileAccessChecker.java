package net.reduck.validator.file;

import java.io.File;
import java.io.IOException;

/**
 * @author Reduck
 * @since 2023/1/30 09:54
 */
public class FileAccessChecker {
    private final String canonicalParentPath;

    public FileAccessChecker(String parentPath) {
        try {
            File file = new File(parentPath);

            if (!file.isDirectory()) {
                throw new RuntimeException("File must be directory");
            }
            this.canonicalParentPath = file.getCanonicalPath() + File.separatorChar;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileAccessChecker(File parentFile) {
        try {
            this.canonicalParentPath = parentFile.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean access(String filePath) {
        return access(new File(filePath));
    }

    public boolean access(File file) {
        String canonicalPath = null;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            return false;
        }
        return canonicalPath.contains(canonicalParentPath);
    }
}
