package net.reduck.netools;

import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Gin
 * @since 2023/4/17 13:49
 */
public class RemoteCopy extends Thread {
    private final HostInfo hostInfo;

    public RemoteCopy(HostInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    @Override
    public void run() {
        String latest = "";

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String content = DataHelper.getClipboardContent();

            if (content == null || latest.equals(content)) {
                continue;
            }

            latest = content;

            System.out.println("Copy " + latest);
            try {
                new JFtpClient(hostInfo)
                        .connect()
                        .login()
                        .upload(new ByteArrayInputStream(latest.getBytes()), "/tmp/zhanjinkai/copy");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new RemoteCopy(new HostInfo("172.16.245.82", 21, "zhanjinkai", "111111"))
                .start();
    }
}
