package net.reduck.api.doc.descriptor;

import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.Socket;

/**
 * @author Gin
 * @since 2023/4/27 17:14
 */
public class SocketTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 8080);


        socket.getOutputStream().write(
                ("OPTIONS * HTTP/1.1\n" +
                "Host: 127.0.0.1:8080\n" +
                "Connection: keep-alive\n" +
                "sec-ch-ua: \"Chromium\";v=\"100\"\n" +
                "sec-ch-ua-mobile: ?0\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36\n" +
                "sec-ch-ua-platform: \"Windows\"\n" +
                "Accept: */*\n" +
                "Accept-Language: en-US\n" +
                "Sec-Fetch-Site: same-origin\n" +
                "Sec-Fetch-Mode: no-cors\n" +
                "Sec-Fetch-Dest: script\n" +
//                "Referer: https://192.168.31.119:8443/index.html\n" +
                "Content-Length: 0").getBytes());

        System.out.println("OPTIONS * HTTP/1.1\n" +
                "Host: 127.0.0.1:8080\n" +
                "Connection: keep-alive\n" +
                "sec-ch-ua: \"Chromium\";v=\"100\"\n" +
                "sec-ch-ua-mobile: ?0\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36\n" +
                "sec-ch-ua-platform: \"Windows\"\n" +
                "Accept: */*\n" +
                "Accept-Language: en-US\n" +
                "Sec-Fetch-Site: same-origin\n" +
                "Sec-Fetch-Mode: no-cors\n" +
                "Sec-Fetch-Dest: script\n" +
//                "Referer: https://192.168.31.119:8443/index.html\n" +
                "Content-Length: 0");
//        socket.getOutputStream().close();
        socket.getOutputStream().flush();
        socket.shutdownOutput();
        System.out.println(333);
//        InputStream in = socket.getInputStream();

//        System.out.println(new String(FileCopyUtils.copyToByteArray(in)));

        main2();
    }

    public static void main2() throws IOException, InterruptedException {
        String host = "127.0.0.1";
        int port = 8080;
        String path = "*";
        String method = "OPTIONS";
        Socket socket = new Socket(host, port);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer.println(method + " " + path + " HTTP/1.1");
        writer.println("Host: " + host);
        writer.println("Accept: */*");
        writer.println();
        String response;
        while ((response = reader.readLine()) != null) {
            System.out.println(response);
        }
        reader.close();
        writer.close();
        socket.close();

        Thread.sleep(1000000);
    }
}
