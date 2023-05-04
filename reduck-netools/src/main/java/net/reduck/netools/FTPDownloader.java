package net.reduck.netools;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Gin
 * @since 2023/4/17 14:50
 */
public class FTPDownloader {

    public static void main(String[] args) {
        String server = "172.16.245.82"; // FTP 服务器地址
        int port = 21; // FTP 服务器端口号
        String username = "zhanjinkai"; // FTP 用户名
        String password = "111111"; // FTP 密码
        String remoteFilePath = "/TMP/zhanjinkai/copy"; // 远程文件路径
        String localFilePath = "/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/src/main/resources/copy"; // 本地文件保存路径

        FTPClient ftpClient = new FTPClient();

        try {
            // 连接 FTP 服务器
            ftpClient.connect(server, port);
            ftpClient.login(username, password);

            // 设置文件传输模式为二进制
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 下载文件
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
            OutputStream outputStream = new FileOutputStream(localFilePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
            ftpClient.completePendingCommand();

            System.out.println("文件下载成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 断开连接
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
