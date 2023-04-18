package net.reduck.netools;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.scp.client.ScpClient;
import org.apache.sshd.scp.client.SimpleScpClient;
import org.apache.sshd.scp.client.SimpleScpClientImpl;

import java.io.File;
import java.io.IOException;

/**
 * @author Gin
 * @since 2023/4/4 16:10
 */
public class JScpClient {
    private final HostInfo hostInfo;

    public JScpClient(HostInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    public void upload(String localFilePath, String remoteDirPath) {
        File file = new File(localFilePath);

        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }

        try (SshClient client = SshClient.setUpDefaultClient()) {
            try (SimpleScpClient scpClient = new SimpleScpClientImpl(SshClient.wrapAsSimpleClient(client))) {
                client.start();
                ScpClient scp = scpClient.scpLogin(hostInfo.getHost(), hostInfo.getPort(), hostInfo.getUsername(), hostInfo.getPassword());
                if (file.isDirectory()) {
                    scp.upload(localFilePath, remoteDirPath, ScpClient.Option.Recursive, ScpClient.Option.PreserveAttributes);
                } else {
                    scp.upload(localFilePath, remoteDirPath, ScpClient.Option.PreserveAttributes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        new JScpClient("172.16.44.181", "root", "1")
//                .upload("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/mvnw.cmd", "/opt");
//
//        new JScpClient("172.16.44.181", "root", "1")
//                .upload("/Users/zhanjinkai/Downloads/dbf", "/opt");
    }
}
