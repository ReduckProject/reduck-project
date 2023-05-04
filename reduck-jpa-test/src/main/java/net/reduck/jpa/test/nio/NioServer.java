package net.reduck.jpa.test.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Gin
 * @since 2023/3/7 10:54
 */
public class NioServer {

    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel，-->> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(5555);
        serverSocketChannel.socket().bind(inetSocketAddress);
        serverSocketChannel.configureBlocking(false); //设置成非阻塞

        //开启selector,并注册accept事件
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select(2000);  //监听所有通道
            //遍历selectionKeys
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println(selectionKeys.size());
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {  //处理连接事件
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);  //设置为非阻塞
                    System.out.println("client:" + socketChannel.getLocalAddress() + " is connect");
                    socketChannel.register(selector, SelectionKey.OP_READ); //注册客户端读取事件到selector
                } else if (key.isReadable()) {//处理读取事件
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    SocketChannel channel = (SocketChannel) key.channel();

                    System.out.println("client:" + channel.getLocalAddress() + " send " + process(channel, byteBuffer));

//                    channel.read(byteBuffer);


                    if (channel.socket().isClosed()) {
                        channel.close();
                    }

                }
                iterator.remove();  //事件处理完毕，要记得清除
            }
        }
    }

    private static String process(SocketChannel channel, ByteBuffer buf) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        while (channel.read(buf) > 0) {// 读取通道中的数据，并写入到 buf 中
            buf.flip(); // 缓存区切换到读模式
            System.out.println(buf.limit());
            while (buf.position() < buf.limit()) { // 读取 buf 中的数据
                os.write(buf.get());
            }
            buf.clear(); // 清空 buffer，缓存区切换到写模式
        }

        byte[] data = os.toByteArray();

        return new String(os.toByteArray());
    }
}
