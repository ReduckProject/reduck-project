package net.reduck.jpa.entity.transformer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Gin
 * @since 2023/9/7 11:27
 */
public class TimeDescTransformer implements ColumnTransformer<Long, String> {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public String toAttribute(Long attribute) {
        if(attribute == null) {
            return "";
        }

        return format.format(new Date(attribute));
    }

}
