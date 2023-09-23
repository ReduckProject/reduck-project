package net.reduck.jpa.specification;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import net.reduck.jpa.entity.transformer.ColumnTransformer;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.beans.PropertyDescriptor;
import java.util.Map;

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

    private JoinType joinType = JoinType.LEFT;

    Class<? extends ColumnTransformer> transformer;

    private ColumnTransformer instance;

    @SneakyThrows
    public void fill(Object item, Object data) {
        if(data == null && descriptor.getPropertyType().isPrimitive()) {
            return;
        }

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

    /**
     *
     * @param root
     * @return
     *
     * @deprecated 存在多次join同一实体的问题
     */
    @Deprecated
    public Path<?> selection(Root<?> root) {
        Path<?> path = null;

        if (join != null) {
            for (String relation : join) {
                path = path == null ? root.join(relation, joinType) : ((Join)path).join(relation, joinType);
            }
        }

        return path == null ? root.get(getColumnName()) : path.get(getColumnName());
    }

    public Path<?> selection(Root<?> root, Map<String, Join> joinMap) {

        if (join != null && join.length > 0) {
            Join joinTable = JoinParser.getJoin(root, join, joinType, joinMap);
            return joinTable.get(getColumnName());
        }

        return root.get(getColumnName());
    }

}
