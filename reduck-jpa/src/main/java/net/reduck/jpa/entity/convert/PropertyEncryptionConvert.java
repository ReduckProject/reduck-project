package net.reduck.jpa.entity.convert;

import javax.persistence.AttributeConverter;

/**
 * @author Gin
 * @since 2022/9/29 09:52
 */
public class PropertyEncryptionConvert implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return "";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return "";
    }
}
