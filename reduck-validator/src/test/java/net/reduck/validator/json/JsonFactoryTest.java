package net.reduck.validator.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.Data;
import net.reduck.validator.JsonUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Gin
 * @since 2023/3/9 11:29
 */
public class JsonFactoryTest {
    private final String data = "{\n" +
            "  \"id\":1125687077,\n" +
            "  \"text\":\"@stroughtonsmith You need to add a \\\"Favourites\\\" tab to TC/iPhone. Like what TwitterFon did. I can't WAIT for your Twitter App!! :) Any ETA?\",\n" +
            "  \"fromUserId\":855523, \n" +
            "  \"toUserId\":815309,\n" +
            "  \"languageCode\":\"en\"\n" +
            "}";

    @Test
    public void testRead() throws IOException {
        JsonFactory jsonF = new JsonFactory();
        JsonParser jp = jsonF.createParser(data);
        TwitterEntry entry = read(jp);

        System.out.println(JsonUtils.object2Json(entry));
    }

    @Data
    public static class TwitterEntry {
        long _id;
        String _text;
        int _fromUserId, _toUserId;
        String _languageCode;

        public void setId(long id) { _id = id; }
        public void setText(String text) { _text = text; }
        public void setFromUserId(int id) { _fromUserId = id; }
        public void setToUserId(int id) { _toUserId = id; }
        public void setLanguageCode(String languageCode) { _languageCode = languageCode; }

        public int getId() { return (int) _id; }
        public String getText() { return _text; }
        public int getFromUserId() { return _fromUserId; }
        public int getToUserId() { return _toUserId; }
        public String getLanguageCode() { return _languageCode; }
    }

    TwitterEntry read(JsonParser jp) throws IOException
    {
        // Sanity check: verify that we got "Json Object":
        if (jp.nextToken() != JsonToken.START_OBJECT) {
            throw new IOException("Expected data to start with an Object");
        }
        TwitterEntry result = new TwitterEntry();
        // Iterate over object fields:
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jp.getCurrentName();
            // Let's move to value
            jp.nextToken();
            if (fieldName.equals("id")) {
                result.setId(jp.getLongValue());
            } else if (fieldName.equals("text")) {
                result.setText(jp.getText());
            } else if (fieldName.equals("fromUserId")) {
                result.setFromUserId(jp.getIntValue());
            } else if (fieldName.equals("toUserId")) {
                result.setToUserId(jp.getIntValue());
            } else if (fieldName.equals("languageCode")) {
                result.setLanguageCode(jp.getText());
            } else { // ignore, or signal error?
                throw new IOException("Unrecognized field '"+fieldName+"'");
            }
        }
        jp.close(); // important to close both parser and underlying File reader
        return result;
    }
}
