package net.reduck.netools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Gin
 * @since 2023/4/14 10:27
 */
@Slf4j
public class JFtpClient {
    FTPClient ftpClient = new FTPClient();

    private final HostInfo hostInfo;

    public JFtpClient(HostInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    public JFtpClient connect() throws IOException {
        ftpClient.connect(hostInfo.getHost(), hostInfo.getPort());
        return this;
    }

    public JFtpClient login() throws IOException {
        if(!ftpClient.login(hostInfo.getUsername(), hostInfo.getPassword())){
            log.warn("Login {}:{} with {} failed", hostInfo.getHost(), hostInfo.getPort(), hostInfo.getUsername());
            throw new RuntimeException("登录失败");
        }


        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        return this;
    }

    public JFtpClient charset(Charset charset){
        ftpClient.setCharset(charset);
        return this;
    }
    public JFtpClient download(String remoteFilePath, String localFilePath) throws IOException {
        // 下载文件
        File fileToDownload = new File(localFilePath);
        OutputStream outputStream = Files.newOutputStream(fileToDownload.toPath());
        ftpClient.retrieveFile(remoteFilePath, outputStream);
        outputStream.close();
        return this;
    }

    public JFtpClient download(String remoteFilePath, String localFilePath, String pattern) throws IOException {

        // 下载文件
        File fileToDownload = new File(localFilePath);
        OutputStream outputStream = Files.newOutputStream(fileToDownload.toPath());
        ftpClient.retrieveFile(remoteFilePath, outputStream);
        outputStream.close();
        return this;
    }

    public void printIfChange(String remoteFilePath) throws IOException, InterruptedException {
        long latest = 0;
        while (true){
            Thread.sleep(1000);
            System.out.println(1);
            FTPFile[] file = ftpClient.listFiles(remoteFilePath);

            if(file != null && file.length > 0) {
                long current = file[0].getTimestamp().getTime().getTime();
                if(latest == current){
                    continue;
                }

                latest = current;

                System.out.println(current);
            }else {
                continue;
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ftpClient.retrieveFile(remoteFilePath, outputStream);
            System.out.println("2:"  + new String(FileCopyUtils.copyToByteArray(ftpClient.retrieveFileStream(file[0].getName()))));
        }
    }

    public JFtpClient list(String parent) throws IOException {
        FTPFile[] ftpFiles = ftpClient.listDirectories(parent);
        Deque<FTPFile> deque = new ArrayDeque<>();

        for(FTPFile file : ftpFiles){
            deque.push(file);
        }

        while (!deque.isEmpty()) {
            FTPFile file = deque.pop();
            file.setLink(parent);
            log.info(file.getName());
            if(file.isDirectory()) {
                if(".".equals(file.getName()) || "..".equals(file.getName())){
                    continue;
                }
                FTPFile[] subDir = ftpClient.listDirectories(file.getLink() + "/" + file.getName());
                for(FTPFile subFile : subDir) {
                    subFile.setLink(file.getLink() + "/" + file.getName());
                    deque.push(subFile);
                }
            }
        }

        return this;
    }

    public JFtpClient upload(String localFilePath, String remotePath) throws IOException {

        // 设置文件类型为二进制
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        // 上传文件
        File fileToUpload = new File(localFilePath);
        InputStream inputStream = new FileInputStream(fileToUpload);
        if(!ftpClient.storeFile(remotePath, inputStream)){
            log.warn("Upload {} to {} failed", localFilePath, remotePath);
        }
        inputStream.close();
        return this;
    }

    public JFtpClient upload(InputStream in, String remotePath) throws IOException {

        // 设置文件类型为二进制
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        if(!ftpClient.storeFile(remotePath, in)){
            log.warn("Upload {} to {} failed","in", remotePath);
        }
        in.close();
        return this;
    }

    public JFtpClient logout() throws IOException {
        ftpClient.logout();
        return this;
    }

    public JFtpClient disconnect() throws IOException {
        ftpClient.disconnect();
        return this;
    }



    public static void main(String[] args) throws IOException, InterruptedException {
//        new JFtpClient(new HostInfo("172.16.245.82", 21, "zhanjinkai", "111111"))
//                .connect()
//                .login()
//                .upload("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/src/test/java/net/reduck/netools/AlertTest.java"
//                        , "/TMP/zhanjinkai/AlertTest2.java")
//                .list("/tmp/zhanjinkai")
//                .logout()
//                .disconnect();

//        new JFtpClient(new HostInfo("124.221.119.11", 21, "ginsco", "txc_ftp_login"))
//                .connect()
//                .login()
//                .upload("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/src/test/java/net/reduck/netools/AlertTest.java"
//                        , "/")
////                .list("/tmp/zhanjinkai")
//                .logout()
//                .disconnect();

        new JFtpClient(new HostInfo("172.16.245.82", 21, "zhanjinkai", "111111"))
                .connect()
                .login()
//                .download("/tmp/zhanjinkai/copy", "/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/src/main/resources/copy");
                .printIfChange("/tmp/zhanjinkai/copy");
    }
}
