package net.reduck.jpa.specification;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import net.reduck.jpa.entity.transformer.ColumnTransformer;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.beans.PropertyDescriptor;

/**
 * @author Gin
 * @since 2023/9/12 11:27
 */
@Data
@Accessors(chain = true)
public class ColumnProjectionDescriptor {

    private String fieldName;

    private String name;

    private String[] join;

    PropertyDescriptor descriptor;

    Class<? extends ColumnTransformer> transformer;

    private ColumnTransformer instance;

    @SneakyThrows
    public void fill(Object item, Object data) {
        if (this.getTransformer() != null && this.getTransformer() != ColumnTransformer.class) {
            if (instance == null) {
                instance = transformer.newInstance();
            }
            this.descriptor.getWriteMethod().invoke(item, instance.toAttribute(data));
        } else {
            this.descriptor.getWriteMethod().invoke(item, data);
        }
    }

    public String getColumnName() {
        return StringUtils.hasText(this.name) ? this.name : fieldName;
    }

    public Path<?> selection(Root<?> root) {
        Path<?> path = null;

        if (join != null) {
            for (String relation : join) {
                path = path == null ? root.get(relation) : path.get(relation);
            }
        }

        return path == null ? root.get(getColumnName()) : path.get(getColumnName());
    }
}
