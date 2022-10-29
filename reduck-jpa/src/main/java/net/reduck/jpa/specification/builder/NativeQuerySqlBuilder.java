package net.reduck.jpa.specification.builder;

/**
 * @author Reduck
 * @since 2022/9/23 15:26
 */
public class NativeQuerySqlBuilder {
    private final StringBuilder sqlBuilder = new StringBuilder();
    private String[] columns = null;
    private String from = null;
    private String where = null;
    private String[] orderBy = null;
    private String[] groupBy = null;
    private int[] limit = null;
    private Object[] params = null;

    public NativeQuerySqlBuilder select(String... columns) {
        this.columns = columns;
        return this;
    }

    public NativeQuerySqlBuilder from(String table) {
        this.from = table;
        return this;
    }

    public NativeQuerySqlBuilder where(String statement) {
        return this;
    }

    public NativeQuerySqlBuilder orderBy(String... orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public NativeQuerySqlBuilder groupBy(String... groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public NativeQuerySqlBuilder limit(int... limit){
        this.limit = limit;
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        if (columns == null) {
            throw new IllegalArgumentException("select columns not find");
        }
        sb.append("select ");
        for (int i = 0; i < columns.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }

            sb.append(columns[i]);
        }

        if (from == null) {
            throw new IllegalArgumentException("table not find");
        }
        sb.append(" from ").append(from);

        if (where != null) {

        }

        if (orderBy != null) {
            sb.append(" order by ");
            for (int i = 0; i < orderBy.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }

                sb.append(orderBy[i]);
            }
        }

        if (groupBy != null) {
            sb.append(" group by ");
            for (int i = 0; i < groupBy.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(groupBy[i]);
            }
        }

        if(limit != null){
            sb.append(" limit ");
            for (int i = 0; i < limit.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(limit[i]);
            }
        }

        return sb.toString();
    }
}
