package net.reduck.validator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Reduck
 * @since 2020/11/6 18:03
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper mapperFormatter = new ObjectMapper();

    static {
        mapperFormatter.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }


    public static <T> T json2Object(String str, Class<T> t) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        if (String.class.equals(t)) {
            return (T) str;
        }

        try {
            return mapper.readValue(str, t);
        } catch (IOException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Object(byte[] data, Class<T> t) {
        if (data == null || data.length == 0) {
            return null;
        }

        try {
            return mapper.readValue(data, t);
        } catch (IOException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static String object2Json(Object o) {
        try {
            if (o instanceof String) {
                return (String) o;
            }
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Object(String content, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        try {
            return mapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> Map<String, T> json2Map(String json, Class<?> elementClasses) {
        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructParametricType(Map.class, String.class, elementClasses)
            );
        } catch (JsonProcessingException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static String object2JsonFormat(Object o) {
        try {
            if (o instanceof String) {
                return jsonFormat(o.toString());
            }

            return mapperFormatter.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> json2List(String content, Class<T> t) {
        JavaType javaType = getCollectionType(ArrayList.class, t);
        try {
            return mapper.readValue(content, javaType);
        } catch (Exception e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T cast(Object original, Class<T> target) {
        return json2Object(object2Json(original), target);
    }

    public static String jsonFormat(String data) {
        return object2JsonFormat(json2Object(data, HashMap.class));
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static void main(String[] args) throws IOException {
        Map<String, Map<String, String>> map = new HashMap<>();

        Map<String, String> node = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            node.put("key" + i, "value_____value____" + i);
        }

        for (int i = 0; i < 100000; i++) {
            map.put("node" + i, node);
        }

        String filePath = "/Users/zhanjinkai/Downloads/test.json";
        long t1 = System.currentTimeMillis();

        FileCopyUtils.copy(JsonUtils.object2JsonFormat(map), new FileWriter(filePath));

        long t2 = System.currentTimeMillis();
        Map map1 = JsonUtils.json2Map(FileCopyUtils.copyToString(new FileReader(filePath)), Map.class);

        System.out.println(map1.size());

        long t3 = System.currentTimeMillis();

        System.out.println("toJson " + (t2 - t1) + ", toMap " + (t3 - t2));
    }
}
