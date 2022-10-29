package net.reduck.jpa.specification;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@SuppressWarnings("unchecked")
public class PaginationResult<R> {
    private long total;
    private int totalPages;
    private List<R> rows;

    public PaginationResult(long total, int totalPages, List<R> rows) {
        this.total = total;
        this.totalPages = totalPages;
        this.rows = rows;
    }

    public PaginationResult() {
    }

    <T> PaginationResult<R> of(Page<T> page, Function<T, R> transferData) {
        this.rows = page.getContent().stream().map(transferData).collect(Collectors.toList());
        this.total = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        return this;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<R> getRows() {
        return rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setRows(List<R> rows) {
        this.rows = rows;
    }

    public static <R> PaginationResult<R> init(Page<?> page, List<R> rows) {
        return new PaginationResult(page.getTotalElements(), page.getTotalPages(), rows);
    }

    public static <R> PaginationResult<R> init(Page<R> page) {
        return new PaginationResult(page.getTotalElements(), page.getTotalPages(), page.getContent());
    }

    public static <R> PaginationResult<R> init(long total, int totalPages, List<R> rows) {
        return new PaginationResult(total, totalPages, rows);
    }

}
