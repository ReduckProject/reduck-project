package net.reduck.netools;

import lombok.SneakyThrows;

/**
 * @author Gin
 * @since 2023/4/17 14:08
 */
public class RemotePaste extends Thread {

    private final HostInfo hostInfo;

    public RemotePaste(HostInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    @SneakyThrows
    @Override
    public void run() {
        new JFtpClient(hostInfo)
                .connect()
                .login();

    }
}
