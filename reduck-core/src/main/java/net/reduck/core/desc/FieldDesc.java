package net.reduck.core.desc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gin
 * @since 2023/2/6 15:58
 */
@AllArgsConstructor
@Getter
public class FieldDesc {

    private String name;

    public String getGetterName() {
        if("boolean".equals(name)) {
            return "is" + getSuffix();
        }
        return "get" + getSuffix();
    }

    public String getSetterName() {
        return "set" + getSuffix();
    }

    private String getSuffix() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static void main(String[] args) {
        long x = 3;

    }
}
