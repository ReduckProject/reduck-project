package net.reduck.chat.server.utils;

/**
 * @author Reduck
 * @since 2023/1/12 17:52
 */
public interface MessageSign {
    byte[] HEADER_START = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};

    byte[] HEADER_END = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02};

    byte[] BODY_START = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03};

    byte[] BODY_END = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04};

    byte[] ATTACH_START = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x05};

    byte[] ATTACH_END = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06};
}
