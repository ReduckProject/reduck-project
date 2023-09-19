package net.reduck.jpa.specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * @author Gin
 * @since 2023/9/19 10:26
 */
public class JoinParser {

    static Join getJoin(Root root, String[] joinNames, JoinType joinType, Map<String, Join> joinMap) {
        StringBuilder sb = new StringBuilder();
        Join join = null;

        for (String joinName : joinNames) {
            sb.append(joinName).append(".");
            if (!joinMap.containsKey(sb.toString())) {
                if (join == null) {
                    joinMap.put(sb.toString(), root.join(joinName, joinType));
                } else {
                    joinMap.put(sb.toString(), join.join(joinName, joinType));
                }
            }
            join = joinMap.get(sb.toString());
        }
        return join;
    }
}
