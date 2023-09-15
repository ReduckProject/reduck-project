package net.reduck.jpa.test.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import net.reduck.validator.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gin
 * @since 2023/6/29 11:31
 */

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type" ,visible = false)
@JsonSubTypes({
        @JsonSubTypes.Type(value = J1.class, name = "j1"),
        @JsonSubTypes.Type(value = J2.class, name = "j2")
})
public abstract class BaseJ {

    public abstract String getType();

    public enum T {
        a,b,c;
    }

    public static void main(String[] args) {
        List<BaseJ> baseJS = new ArrayList<>();
        baseJS.add(new J1());
        baseJS.add(new J2());

        String json = "{\"password\":null,\"type\":\"j2\"}";
        System.out.println(JsonUtils.object2Json(baseJS));

        J2 j2 = (J2) JsonUtils.json2Object(json, BaseJ.class);
        System.out.println();

    }
}
