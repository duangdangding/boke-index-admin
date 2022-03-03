package com.pearadmin.boke.vo;

import java.io.Serializable;
import java.util.List;

public class BootStrapResult<T> implements Serializable {
    
    private Integer pageNumber;
    private Integer pageSize;
    private List<T> rows;
    private Long total;
    
    public BootStrapResult(List<T> rows,Long total) {
        this.rows = rows;
        this.total = total;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
