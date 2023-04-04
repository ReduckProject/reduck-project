package net.reduck.netools;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Gin
 * @since 2023/4/4 15:37
 */
public class JSchSSHUtil {

    /**
     * JSch，执行多条语句<
     * pty 交互式命令
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param commands
     */

    public static void ssh2Shell(String host, int port, String username, String password, String[] commands) {
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
                for (String cmd : commands) {
                    pw.println(cmd);
                }
                pw.println("exit");
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

                        System.out.println(s);
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

    /**
     * JSch，执行多条语句
     * commond 一次命令
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param commands
     *
     * @return
     */
    public static String ssh2ShellExec(String host, int port, String username, String password, String[] commands) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelExec channelExec = null;
        String result = "";
        try {
            session = jsch.getSession(username, host, port);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshConfig.put("PreferredAuthentications", "publickey,keyboard-interactive,password");//新环TDH 需要
            session.setConfig(sshConfig);
            session.setPassword(password);
            session.connect();
            channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setPty(false);
            String command = commands[0];
            for (int i = 1; i < commands.length; i++) {
                command += " && " + commands[i];
            }
            channelExec.setCommand(command);
            InputStream std = channelExec.getInputStream();
            InputStream error = channelExec.getErrStream();
            BufferedReader brStd = new BufferedReader(new InputStreamReader(std, StandardCharsets.UTF_8));
            BufferedReader brError = new BufferedReader(new InputStreamReader(error, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = brStd.readLine()) != null) {
                if (StringUtils.isNotBlank(line)) {
                    sb.append(line);
                }
            }

            while ((line = brError.readLine()) != null) {
                if (StringUtils.isNotBlank(line)) {
                    sb.append(line);
                }
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channelExec != null) {
                channelExec.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ssh2Shell("172.16.44.181", 22, "root", "1", new String[]{"ls", "ls -a"});
    }
}
