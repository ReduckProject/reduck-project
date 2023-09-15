package net.reduck.jpa.specification;

import net.reduck.jpa.specification.annotation.AttributeIgnore;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.ws.rs.DefaultValue;

public class PageRequest {
    @AttributeIgnore
    @DefaultValue("15")
    private int rows = 15;

    @AttributeIgnore
    @DefaultValue("1")
    private int page = 1;

    @AttributeIgnore
    @DefaultValue("id")
    private String sortProperty = "id";

    @AttributeIgnore
    private Sort.Direction sortDirection = Sort.Direction.DESC;

    public PageRequest() {
    }

    public PageRequest(int rows, int page) {
        this.rows = rows;
        this.page = page;
    }

    public PageRequest(int rows, int page, String sortProperty, Sort.Direction sortDirection) {
        this.rows = rows;
        this.page = page;
        this.sortProperty = sortProperty;
        this.sortDirection = sortDirection;
    }

    @AttributeIgnore
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @AttributeIgnore
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @AttributeIgnore
    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    @AttributeIgnore
    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public org.springframework.data.domain.PageRequest toPageable() {
        if (this.rows <= 0) {
            this.rows = 15;
        }

        if (this.page <= 0) {
            this.page = 1;
        }

        if (StringUtils.isEmpty(this.sortProperty)) {
            this.sortProperty = "id";
            this.sortDirection = Sort.Direction.DESC;
        }
        return org.springframework.data.domain.PageRequest.of(this.page - 1, this.rows, this.sortDirection, this.sortProperty);

    }

    public Boolean getDeleted() {
        return false;
    }
}
