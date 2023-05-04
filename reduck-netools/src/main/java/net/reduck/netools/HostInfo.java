package net.reduck.netools;

import lombok.Data;

/**
 * @author Gin
 * @since 2023/4/14 10:29
 */

@Data
public class HostInfo {

    private final String host;
    private final int port;

    private final String username;
    private final String password;

    public HostInfo(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
