package net.reduck.netools;

import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * @author Gin
 * @since 2023/4/12 16:59
 */
public class AlertTest {


    public static void main(String[] args) {
        showMessageDialog("Hello, World!", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showMessageDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
