package net.reduck.data.protection.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Reduck
 * @since 2022/8/26 11:43
 */
public class DataMaskingModule extends Module {
    private final DataMaskingExecutor executor;

    public DataMaskingModule(DataMaskingExecutor executor) {
        this.executor = executor;
    }

    @Override
    public String getModuleName() {
        return "DataMaskingModule";
    }

    @Override
    public Version version() {
        return VersionUtil.parseVersion("1.0", "net.reduck", "reduck-data-protection");
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        setupContext.addBeanSerializerModifier(new DataMaskingSerializerModifier(this.executor));
    }

    public static class DataMaskingSerializerModifier extends BeanSerializerModifier {
        private final DataMaskingExecutor executor;

        public DataMaskingSerializerModifier(DataMaskingExecutor executor) {
            this.executor = executor;
        }

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            List<BeanPropertyWriter> newWriter = new ArrayList<>();
            for (BeanPropertyWriter writer : beanProperties) {
                if (null != writer.getAnnotation(DataMasking.class)) {
                    writer.assignSerializer(new DataMaskingJsonSerializer(writer.getSerializer(), executor));
                }
                newWriter.add(writer);
            }

            return newWriter;
        }
    }

    public static class DataMaskingJsonSerializer extends JsonSerializer<Object> {
        private final JsonSerializer<Object> serializer;
        private final DataMaskingExecutor executor;

        public DataMaskingJsonSerializer(JsonSerializer<Object> serializer, DataMaskingExecutor executor) {
            this.serializer = serializer;
            this.executor = executor;
        }

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (serializer == null) {
                serializers.defaultSerializeValue(executor.masking(value), gen);
                return;

            }
            serializer.serialize(executor.masking(value), gen, serializers);
        }
    }
}
