package net.reduck.jpa.entity.convert;

import javax.persistence.AttributeConverter;

/**
 * @author Reduck
 * @since 2022/9/29 09:52
 */
public class PropertyEncryptionConvert implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return "enc";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return "dec";
    }
}
