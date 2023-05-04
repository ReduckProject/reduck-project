package net.reduck.netools;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Gin
 * @since 2023/4/14 10:35
 */
@Slf4j
public class WindowsHelper {

    public static void main(String[] args) {
        openBrowser("https://www.baidu.com");
    }
    public static boolean openBrowser(String url){
        try {
            // 获取 Desktop 实例
            Desktop desktop = Desktop.getDesktop();
            // 检查是否支持浏览器操作
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                // 创建一个 URI 对象表示要访问的 URL
                URI uri = new URI(url);
                // 打开默认浏览器并显示 URL
                desktop.browse(uri);
            }
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
