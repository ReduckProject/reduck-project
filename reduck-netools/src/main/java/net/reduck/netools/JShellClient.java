package net.reduck.netools;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Gin
 * @since 2023/4/12 12:40
 */
public class JShellClient {
    private final String host;
    private final int port;

    private final String username;
    private final String password;

    JSch jsch = new JSch();

    public JShellClient(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public JShellClient(String host, String username, String password) {
        this.host = host;

        // 默认端口22
        this.port = 22;
        this.username = username;
        this.password = password;
    }


    public void connect() throws JSchException, IOException {
        Session session = getSession();
        session.connect();

        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        channelShell.setPty(true);
        channelShell.connect();

        PrintWriter writer = new PrintWriter(channelShell.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(channelShell.getInputStream()));
        out(channelShell.getInputStream());
        while ((input = reader.readLine()) != null) {
//            System.out.println(input);
//            writer.write(input);
            writer.println(input);
            writer.flush();

//            while (responseIn.available() > 0) {
//                System.out.println(new String(FileCopyUtils.copyToByteArray(responseIn)));
//            }
        }
    }

    private Session getSession() throws JSchException {
        Session session = jsch.getSession(username, host, port);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        session.setPassword(password);

        return session;
    }

    private void out(InputStream in) {
        new Thread(() -> {
            String out = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while (true) {
                try {
                    int count = 0;
                    if (((out = reader.readLine()) != null)) {
//                        if(out.startsWith("[") && !out.endsWith("~]# ")){
//                            break;
//                        }

                        System.out.println(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public void connect2() {
        JSch jsch = new JSch();
        Session session = null;
        ChannelShell channelShell = null;
        try {
            session = jsch.getSession(username, host, port);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.setPassword(password);
            session.connect();
            channelShell = (ChannelShell) session.openChannel("shell");
            channelShell.setPty(true);
            channelShell.connect();
            try (InputStream in = channelShell.getInputStream();
                 OutputStream out = channelShell.getOutputStream();
                 PrintWriter pw = new PrintWriter(out);) {
//                for (String cmd : commands) {
//                    pw.println(cmd);
//                }
//                pw.println("exit");
                pw.flush();

                //日志返回输出
                byte[] buffer = new byte[1024];
                for (; ; ) {
                    while (in.available() > 0) {
                        int i = in.read(buffer, 0, 1024);
                        if (i < 0) {
                            break;
                        }
                        String s = new String(buffer, 0, i);

                        System.out.print(s);
                    }
                    if (channelShell.isClosed()) {
                        System.out.println("SSH2 Exit: " + channelShell.getExitStatus());
                        break;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channelShell != null) {
                channelShell.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    public static void main(String[] args) throws JSchException, IOException {
        JShellClient client = new JShellClient("172.16.44.181", "root", "1");

        client.connect();
    }
}
