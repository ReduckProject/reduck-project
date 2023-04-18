package net.reduck.core.util;

/**
 * @author Gin
 * @since 2023/3/8 11:39
 */
public class Normalizers {
    private final static String SLASH = "/";

    /**
     * URL 格式化
     * @param pathPrefix
     * @param paths
     * @return
     */
    public String getUrlPath(String pathPrefix, String... paths) {
        pathPrefix = pathPrefix == null ? "" : pathPrefix;
        if (pathPrefix.endsWith("/")) {
            pathPrefix = pathPrefix.substring(0, pathPrefix.length() - 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(pathPrefix);

        for (String path : paths) {
            if(!path.startsWith(SLASH)){
                sb.append(path);
            }else {
                sb.append(SLASH).append(path);
            }
        }

        return sb.toString();
    }
}
