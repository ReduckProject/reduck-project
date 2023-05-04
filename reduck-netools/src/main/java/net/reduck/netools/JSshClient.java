package net.reduck.netools;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * @author Gin
 * @since 2023/4/4 17:44
 */
public class JSshClient implements Closeable {
    private final String host;
    private final int port;

    private final String username;
    private final String password;

    public JSshClient(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public JSshClient(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = 22;
    }

    public void test() {
        try (SshClient client = SshClient.setUpDefaultClient()) {

            ClientSession session = client.connect(username, host, port).getClientSession();
            session.addPasswordIdentity(password);

//            session.createShellChannel().
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listFolderStructure(String username, String password,
                                           String host, int port, long defaultTimeoutSeconds, String command) throws IOException {

        SshClient client = SshClient.setUpDefaultClient();
        client.start();

        try (ClientSession session = client.connect(username, host, port)
                .verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()) {
            session.addPasswordIdentity(password);
            session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);

            try (ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                 ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL)) {
                channel.setOut(responseStream);
                try {
                    channel.open().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
                    try (OutputStream pipedIn = channel.getInvertedIn()) {
                        pipedIn.write(command.getBytes());
                        pipedIn.flush();
                    }

                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
                            TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
                    String responseString = new String(responseStream.toByteArray());
                    System.out.println(responseString);
                } finally {
                    channel.close(false);
                }
            }
        } finally {
            client.stop();
        }
    }

    public static void main(String[] args) throws IOException {
        listFolderStructure("root", "1", "172.16.44.181", 22, 100L, "ll");
    }

    @Override
    public void close() throws IOException {

    }
}
