package net.reduck.jpa.test.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author Gin
 * @since 2023/3/7 10:55
 */
public class NioClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 5555);

        if(!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("客户端正在连接中，请耐心等待");
            }
        }

        while (true) {
            Thread.sleep(10);
            ByteBuffer byteBuffer = ByteBuffer.wrap("Nio register".getBytes());
            socketChannel.write(byteBuffer);
            socketChannel.socket().getOutputStream().flush();
        }

//        socketChannel.close();
    }
}
