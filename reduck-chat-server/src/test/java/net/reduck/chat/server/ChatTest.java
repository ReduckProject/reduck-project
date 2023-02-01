package net.reduck.chat.server;

import net.reduck.chat.server.bio.ChatServer;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Reduck
 * @since 2022/11/9 20:38
 */
public class ChatTest {

    @Test
    public void test() throws IOException, InterruptedException {
        new ChatServer().listen(3879);
    }


    @Test
    public void test2() throws IOException {
        Socket socket = new Socket("127.0.0.1", 3879);
        socket.getOutputStream().write("01\n".getBytes());
        socket.getOutputStream().flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String tmp;
        Scanner scanner = new Scanner(System.in);
        while ((tmp = reader.readLine()) != null){
            System.out.println(tmp);
            System.out.println(11);
            String text = scanner.next() + "\n";
            System.out.println(12
            );
            socket.getOutputStream().write(text.getBytes());
        }
    }

    @Test
    public void in() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println(scanner.next());
        }
    }

    @Test
    public void in2(){
        ThreadMXBean tmbean = ManagementFactory.getThreadMXBean();
        tmbean.setThreadContentionMonitoringEnabled(true);
        long[] allThread = tmbean.getAllThreadIds();
        if (allThread.length > 0) {
            for (long threadId : allThread) {
                ThreadInfo info = tmbean.getThreadInfo(threadId);
                Long userTime = info.getWaitedTime();
                log("ThreadId:" + info.getThreadId() + ", ThreadName:" + info.getThreadName() + " ,ThreadState:" + info.getThreadState() + " ,userTime:" + userTime.toString());
            }
        }
    }

    public void log(String text){
        System.out.println(text);
    }

    @Test
    public void test3(){
       for(int i = 10; i < 100; i++){
           System.out.println(i + "\r");
       }
    }
    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        while (true){
//            System.out.println(scanner.next());
//        }
//
//        InputStream in = System.in;
//
//        while (true){
//            System.out.println(in.read());
//        }
//        new ChatTest().test2();

        for(int i = 10; i < 100000000; i++){
            System.out.print(i + "\r");

        }
    }
}
