package net.reduck.netools;

import com.jcraft.jsch.*;

import java.io.ByteArrayInputStream;
import java.util.Properties;

/**
 * @author Gin
 * @since 2023/4/4 15:57
 */
public class _ScpClient {
    private final String host;

    private final int port;

    public _ScpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    private volatile Session session;
    private volatile ChannelSftp channel;

    public synchronized void connect(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig(config);
        session.setInputStream(System.in);
        session.connect();

        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
    }

    public synchronized void uploadFile(String directoryPath, String fileName, byte[] fileBytes, boolean overwrite) throws SftpException, SftpException {
        if (session == null || channel == null) {
            System.err.println("No open session!");
            return;
        }

        // a workaround to check if the directory exists. Otherwise, create it
        channel.cd("/");
        String[] directories = directoryPath.split("/");
        for (String directory : directories) {
            if (directory.length() > 0) {
                try {
                    channel.cd(directory);
                } catch (SftpException e) {
                    // swallowed exception

                    System.out.println("The directory (" + directory + ") seems to be not exist. We will try to create it.");

                    try {
                        channel.mkdir(directory);
                        channel.cd(directory);
                        System.out.println("The directory (" + directory + ") is created successfully!");
                    } catch (SftpException e1) {
                        System.err.println("The directory (" + directory + ") is failed to be created!");
                        e1.printStackTrace();
                        return;
                    }

                }
            }
        }

        channel.put(new ByteArrayInputStream(fileBytes), directoryPath + "/" + fileName, overwrite ? ChannelSftp.OVERWRITE : ChannelSftp.RESUME);
    }

    public synchronized void disconnect() {
        if (session == null || channel == null) {
            System.err.println("No open session!");
            return;
        }

        channel.exit();
        channel.disconnect();
        session.disconnect();

        channel = null;
        session = null;
    }
}
