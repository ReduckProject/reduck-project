package net.reduck.chat.server.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Reduck
 * @since 2020/11/2 11:35
 */
public class Streams {
    private static int BUFFER_SIZE = 4096;

    /**
     * Read stream till EOF is encountered.
     *
     * @param inStr stream to be emptied.
     *
     * @throws IOException in case of underlying IOException.
     */
    public static void drain(InputStream inStr)
            throws IOException {
        byte[] bs = new byte[BUFFER_SIZE];
        while (inStr.read(bs, 0, bs.length) >= 0) {
        }
    }

    /**
     * Read stream fully, returning contents in a byte array.
     *
     * @param inStr stream to be read.
     *
     * @return a byte array representing the contents of inStr.
     */
    public static byte[] readAll(InputStream inStr) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        pipeAll(inStr, buf);
        return buf.toByteArray();
    }

    /**
     * Read from inStr up to a maximum number of bytes, throwing an exception if more the maximum amount
     * of requested data is available.
     *
     * @param inStr stream to be read.
     * @param limit maximum number of bytes that can be read.
     *
     * @return a byte array representing the contents of inStr.
     */
    public static byte[] readAllLimited(InputStream inStr, int limit) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        pipeAllLimited(inStr, limit, buf);
        return buf.toByteArray();
    }

    /**
     * Fully read in buf's length in data, or up to EOF, whichever occurs first,
     *
     * @param inStr the stream to be read.
     * @param buf   the buffer to be read into.
     *
     * @return the number of bytes read into the buffer.
     */
    public static int readFully(InputStream inStr, byte[] buf) {
        return readFully(inStr, buf, 0, buf.length);
    }

    /**
     * Fully read in len's bytes of data into buf, or up to EOF, whichever occurs first,
     *
     * @param inStr the stream to be read.
     * @param buf   the buffer to be read into.
     * @param off   offset into buf to start putting bytes into.
     * @param len   the number of bytes to be read.
     *
     * @return the number of bytes read into the buffer.
     */
    public static int readFully(InputStream inStr, byte[] buf, int off, int len) {
        int totalRead = 0;
        while (totalRead < len) {
            int numRead = 0;
            try {
                numRead = inStr.read(buf, off + totalRead, len - totalRead);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (numRead < 0) {
                break;
            }
            totalRead += numRead;
        }
        return totalRead;
    }


    public static int read(InputStream inStr, byte[] buf) {
        try {
            return inStr.read(buf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] read(InputStream inStr, int maxSize) {
        try {
            byte[] data = new byte[maxSize];
            int size = inStr.read(data);
            if (size == -1) {
                return new byte[0];
            }

            if (size < maxSize) {
                byte[] newData = new byte[size];
                System.arraycopy(data, 0, newData, 0, size);
                return newData;
            }

            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void write(OutputStream os, byte[] data, int off, int len) {
        try {
            os.write(data, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(OutputStream os, byte[] data) {
        try {
            os.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Write the full contents of inStr to the destination stream outStr.
     *
     * @param inStr  source input stream.
     * @param outStr destination output stream.
     *
     * @throws IOException in case of underlying IOException.
     */
    public static void pipeAll(InputStream inStr, OutputStream outStr) {
        byte[] bs = new byte[BUFFER_SIZE];
        int numRead;
        try {
            while ((numRead = inStr.read(bs, 0, bs.length)) >= 0) {
                outStr.write(bs, 0, numRead);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write up to limit bytes of data from inStr to the destination stream outStr.
     *
     * @param inStr  source input stream.
     * @param limit  the maximum number of bytes allowed to be read.
     * @param outStr destination output stream.
     */
    public static long pipeAllLimited(InputStream inStr, long limit, OutputStream outStr) {
        try {
            long total = 0;
            byte[] bs = new byte[BUFFER_SIZE];
            int numRead;
            while ((numRead = inStr.read(bs, 0, bs.length)) >= 0) {
                if ((limit - total) < numRead) {
                    throw new RuntimeException("Data Overflow");
                }
                total += numRead;
                outStr.write(bs, 0, numRead);
            }
            return total;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeBufTo(ByteArrayOutputStream buf, OutputStream output) {
        try {
            buf.writeTo(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
