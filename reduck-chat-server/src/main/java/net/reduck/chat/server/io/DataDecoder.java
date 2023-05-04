package net.reduck.chat.server.io;

import java.nio.charset.Charset;

/**
 * @author Gin
 * @since 2023/2/1 11:47
 */
public interface DataDecoder {

    byte getByte();

    byte[] getByteArray(int length);

    int getInt();

    short getShort();

    long getLong();

    String getString(int length);

    String getString(int length, Charset charset);

    float getFloat();

    double getDouble();

    char getChar();

    boolean getBoolean();

}
