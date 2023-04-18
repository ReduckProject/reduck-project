package net.reduck.netools;

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

/**
 * @author Gin
 * @since 2023/4/14 13:52
 */
public class FTPFileMonitor {

    public static void main(String[] args) throws FileSystemException {
        // 创建 FTP 文件系统管理器
        FileSystemManager fsManager = VFS.getManager();

        // 创建监控的目录，此处示例监控根目录下的 test 目录
        FileObject monitoredDir = fsManager.resolveFile("/Users/zhanjinkai/Downloads/yd_rules.sql");
        // 创建文件监控器
        DefaultFileMonitor fileMonitor = new DefaultFileMonitor(new FileListener() {
            @Override
            public void fileCreated(FileChangeEvent event) throws Exception {
                // 文件创建时触发的操作
                System.out.println("File created: " + event.getFileObject().getName().getBaseName());
            }

            @Override
            public void fileDeleted(FileChangeEvent event) throws Exception {
                // 文件删除时触发的操作
                System.out.println("File deleted: " + event.getFileObject().getName().getBaseName());
            }

            @Override
            public void fileChanged(FileChangeEvent event) throws Exception {
                // 文件变化时触发的操作
                System.out.println("File changed: " + event.getFileObject().getName().getBaseName());
            }
        });

        // 添加监控的目录
        fileMonitor.setRecursive(true);
        fileMonitor.addFile(monitoredDir);
        fileMonitor.setChecksPerRun(1000);
        fileMonitor.setDelay(1000);
        // 启动文件监控器
        fileMonitor.start();

        // 等待文件监控器运行
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
