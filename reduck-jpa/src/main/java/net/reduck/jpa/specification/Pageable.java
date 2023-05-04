package net.reduck.jpa.specification;

/**
 * @author Gin
 * @since 2023/2/9 20:13
 */
public interface Pageable {
    int getRows();

    int getPage();

    boolean isAsc();

    String getSort();
}
