package net.reduck.chat.server.io;

import java.io.*;

/**
 * @author Reduck
 * @since 2023/1/9 11:01
 */
public class MessageReader {
    private final InputStream in;

    private final int bufferSize = 1024 * 2;
    private final byte[] buffer = new byte[bufferSize];
    private int offset = 0;
    private final int reachEnd = -1;

    private byte nextByte = 0;
    private int matchCount = 0;

    private final byte[] endSign;

    public MessageReader(InputStream in, byte[] endSign) {
        this.in = in;
        this.endSign = endSign;
        init();
    }

    public void read(OutputStream os) throws IOException {
        while (offset < buffer.length) {
            int length = buffer.length - offset;
            int read = in.read(buffer, offset, length);
            if (read != reachEnd) {
                offset += read;
            }

            if (offset > 0) {
                for (int i = 0; i < offset; i++) {
                    if (match(buffer[i])) {
                        if (end()) {
                            offset = offset - i - 1;
                            if (offset > 0) {
                                System.arraycopy(buffer, i + 1, buffer, 0, offset);
                            }
                            System.out.println(offset);
                            reset();
                            return;
                        }
                    } else {
                        if (matchCount > 0) {
                            byte[] skipData = new byte[matchCount];
                            System.arraycopy(endSign, 0, skipData, 0, skipData.length);
                            os.write(skipData);
                            reset();
                            if (match(buffer[i])) {
                                // if here, then endSign.length > 1, don't need check end
                                continue;
                            }
                        }
                        os.write(buffer[i]);
                    }
                }
            }

            if (read == reachEnd && offset != 0) {
                throw new EOFException();
            }
        }

        throw new EOFException();
    }

    public void readMessage(OutputStream os) throws IOException {
        read(os);
    }

    public byte[] readMessage() {
        ByteArrayOutputStream os = new ByteArrayOutputStream(bufferSize);
        try {
            read(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return os.toByteArray();
    }

    private void init() {
        if (endSign == null || endSign.length == 0) {
            throw new IllegalArgumentException("endSign must not be null ");
        }
        this.nextByte = endSign[0];
        this.matchCount = 0;
    }

    private boolean match(byte data) {
        if (nextByte == data) {
            matchCount++;
            nextByte = endSign[matchCount % endSign.length];
            return true;
        }

        return false;
    }

    private boolean end() {
        return matchCount == endSign.length;
    }

    private void reset() {
        nextByte = endSign[0];
        matchCount = 0;
    }

    public static void main(String[] args) {
        InputStream in = new ByteArrayInputStream("333#33333313132313123121231316526527656527653376527".getBytes());
        MessageReader reader = new MessageReader(in, "27".getBytes());

        System.out.println(new String(reader.readMessage()));
        System.out.println(new String(reader.readMessage()));
        System.out.println(new String(reader.readMessage()));


        byte[] data = new byte[]{0,1,2,3,4,5,6,7,8};
        System.arraycopy(data, 3, data, 0, 2);

        System.out.println();
    }
}
