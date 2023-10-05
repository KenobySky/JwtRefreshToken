package br.kenobysky.tools;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Shadows
 */
public class DefaultReturnPageableData<T> {

    private List<T> data;

    private Pageable pagination;

    private Object filter;

    private long total;

    public DefaultReturnPageableData(List<T> data, Pageable page, Object filter, long count) {
        this.data = data;
        this.pagination = page;
        this.filter = filter;
        this.total = count;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Object getFilter() {
        return filter;
    }

    public void setFilter(Object filter) {
        this.filter = filter;
    }

    public Pageable getPagination() {
        return pagination;
    }

    public void setPagination(Pageable pagination) {
        this.pagination = pagination;
    }

    public Object getSearch() {
        return filter;
    }

    public void setSearch(String search) {
        this.filter = search;
    }

    public class Pagination {

        private int page;

        private int limit;

        private String sort;

        public Pagination() {
        }

        private Pagination(Page page) {
            this.page = page.getPageable().getPageNumber();
            this.limit = page.getPageable().getPageSize();
            this.sort = page.getSort().toString();

        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

    }

}
