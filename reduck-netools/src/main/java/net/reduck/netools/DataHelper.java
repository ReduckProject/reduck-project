package net.reduck.netools;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author Gin
 * @since 2023/4/17 12:55
 */
public class DataHelper {

    public static String getClipboardContent() {
        // 获取系统剪切板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 获取剪切板中的内容
        Transferable contents = clipboard.getContents(null);

        if (contents != null) {
            // 检查剪切板内容是否包含文本
            if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取文本内容并输出
                    return (String) contents.getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("剪切板内容不包含文本");
            }
        } else {
            System.out.println("剪切板为空");
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(getClipboardContent());
    }
}
