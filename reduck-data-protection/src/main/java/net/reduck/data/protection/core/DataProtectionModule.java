package net.reduck.data.protection.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import net.reduck.data.protection.DataProtectionFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Reduck
 * @since 2022/8/26 11:43
 */
public class DataProtectionModule extends Module {

    private final DataProtectionFactory factory;

    public DataProtectionModule(DataProtectionFactory dataProtectionFactory) {
        this.factory = dataProtectionFactory;
    }

    @Override
    public String getModuleName() {
        return "DataProtectionModule";
    }

    @Override
    public Version version() {
        return VersionUtil.parseVersion("1.0", "net.reduck", "reduck-data-protection");
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        setupContext.addBeanSerializerModifier(new DataMaskingSerializerModifier(factory));
    }

    public static class DataMaskingSerializerModifier extends BeanSerializerModifier {

        private final DataProtectionFactory factory;

        public DataMaskingSerializerModifier(DataProtectionFactory factory) {
            this.factory = factory;
        }

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            List<BeanPropertyWriter> newWriter = new ArrayList<>();
            for (BeanPropertyWriter writer : beanProperties) {
                if (writer.getMember().getAllAnnotations() != null) {
                    for (Annotation annotation : writer.getMember().getAllAnnotations().annotations()) {
                        if (AnnotationUtils.getAnnotation(annotation, DataProtection.class) != null) {
                            writer.assignSerializer(new DataMaskingJsonSerializer(writer.getSerializer(), annotation, factory));
                            break;
                        }
                    }
                }
                newWriter.add(writer);
            }

            return newWriter;
        }
    }

    public static class DataMaskingJsonSerializer extends JsonSerializer<Object> {
        private final JsonSerializer<Object> serializer;
        private final Annotation annotation;
        private final DataProtectionFactory factory;
        private final DataProtector dataProtector;

        public DataMaskingJsonSerializer(JsonSerializer<Object> serializer, Annotation annotation, DataProtectionFactory factory) {
            this.serializer = serializer;
            this.annotation = annotation;
            this.factory = factory;
            this.dataProtector = factory.getDataProtector(annotation.annotationType());
        }

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (serializer == null) {
                serializers.defaultSerializeValue(dataProtector.handle(value), gen);
                return;

            }
            serializer.serialize(dataProtector.handle(value), gen, serializers);
        }
    }
}
