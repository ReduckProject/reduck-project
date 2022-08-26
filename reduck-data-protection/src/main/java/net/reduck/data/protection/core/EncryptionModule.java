package net.reduck.data.protection.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Reduck
 */
public class EncryptionModule extends Module {

    @Override
    public String getModuleName() {
        return "EncryptionModule";
    }

    @Override
    public Version version() {
        return VersionUtil.parseVersion("1.0", "net.reduck", "reduck-data-protection");
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addBeanSerializerModifier(new EncryptedSerializerModifier());
        context.addBeanDeserializerModifier(new EncryptedDeserializerModifier());
    }

    public static class EncryptedSerializerModifier extends BeanSerializerModifier {
        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            List<BeanPropertyWriter> newWriter = new ArrayList<>();
            for (BeanPropertyWriter writer : beanProperties) {
                if (null != writer.getAnnotation(PropertyEncryption.class)) {
                    writer.assignSerializer(new EncryptedJsonSerializer(writer.getSerializer()));
                }
                newWriter.add(writer);
            }

            return newWriter;
        }
    }

    public static class EncryptedDeserializerModifier extends BeanDeserializerModifier {

        @Override
        public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc, BeanDeserializerBuilder builder) {

            Iterator<SettableBeanProperty> propertyIterator = builder.getProperties();
            while (propertyIterator.hasNext()) {
                SettableBeanProperty property = propertyIterator.next();
                if (property.getAnnotation(PropertyEncryption.class) != null && String.class.equals(property.getType().getRawClass())) {
                    builder.addOrReplaceProperty(property.withValueDeserializer(new EncryptedJsonDeserializer(property.getValueDeserializer())), true);
                }
            }

            return builder;
        }
    }

    public static class EncryptedJsonSerializer extends JsonSerializer<Object> {
        private final JsonSerializer<Object> serializer;

        public EncryptedJsonSerializer(JsonSerializer<Object> serializer) {
            this.serializer = serializer;
        }

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (serializer == null) {
                serializers.defaultSerializeValue(EncryptionConverter.getInstance().convertToDatabaseColumn((String) value), gen);
                return;
            }

            if (value instanceof String) {
                serializer.serialize(EncryptionConverter.getInstance().convertToDatabaseColumn((String) value), gen, serializers);
            }
        }
    }

    public static class EncryptedJsonDeserializer extends JsonDeserializer<String> {
        private final JsonDeserializer<Object> deserializer;

        public EncryptedJsonDeserializer(JsonDeserializer<Object> deserializer) {
            this.deserializer = deserializer;
        }

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return EncryptionConverter.getInstance().convertToEntityAttribute(p.getValueAsString());
        }
    }

}
