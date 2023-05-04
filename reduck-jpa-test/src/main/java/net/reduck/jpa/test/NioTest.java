package net.reduck.jpa.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Gin
 * @since 2023/3/7 10:05
 */
public class NioTest {

    public static byte[] connect(String filePath) throws IOException {
        FileChannel channel = FileChannel.open(Paths.get(filePath));

        ByteBuffer  buf = ByteBuffer.allocate(1024);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        while (channel.read(buf) != -1){ // 读取通道中的数据，并写入到 buf 中
            buf.flip(); // 缓存区切换到读模式
            while (buf.position() < buf.limit()){ // 读取 buf 中的数据
                os.write(buf.get());
            }
            buf.clear(); // 清空 buffer，缓存区切换到写模式
        }


        channel.close();
        return os.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new String(connect("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-jpa-test/src/main/resources/application.properties")));
    }
}
