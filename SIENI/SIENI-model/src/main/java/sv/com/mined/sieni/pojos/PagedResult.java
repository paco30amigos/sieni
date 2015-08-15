/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos;

import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author francisco_medina
 * @param <T>
 */
public class PagedResult<T> {

    private int first;
    private int pageSize;
    private String sortField;
    private SortOrder sortOrder;
    private Map<String, String> filters;
    private List<T> resutl;
    private int count;

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public List<T> getResutl() {
        return resutl;
    }

    public void setResutl(List<T> resutl) {
        this.resutl = resutl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
