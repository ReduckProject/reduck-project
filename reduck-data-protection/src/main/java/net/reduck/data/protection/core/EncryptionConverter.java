package net.reduck.data.protection.core;

/**
 * @author Reduck
 * @since 2021/3/20 13:04
 */
public class EncryptionConverter {
    private final static EncryptionConverter INSTANCE = new EncryptionConverter();

    public static EncryptionConverter getInstance() {
        return INSTANCE;
    }

    public String convertToDatabaseColumn(String attribute) {
        return "convertToDatabaseColumn";
    }

    public String convertToEntityAttribute(String dbData) {
        return "convertToEntityAttribute";
    }
}
