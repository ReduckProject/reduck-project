package net.reduck.jpa.specification;

import net.reduck.jpa.specification.annotation.SpecificationIgnore;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.ws.rs.DefaultValue;

public class PageRequest {
    @SpecificationIgnore
    @DefaultValue("15")
    private int rows = 15;

    @SpecificationIgnore
    @DefaultValue("1")
    private int page = 1;

    @SpecificationIgnore
    @DefaultValue("id")
    private String sortProperty = "id";

    @SpecificationIgnore
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

    @SpecificationIgnore
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @SpecificationIgnore
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @SpecificationIgnore
    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    @SpecificationIgnore
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
