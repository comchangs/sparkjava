package com.sh.pojo.common;

public class Page {

    private Integer page;

    private Integer pageLimit;

    public Page() {
    }

    public Page(Integer page, Integer pageLimit) {
        setPage(page);
        setPageLimit(pageLimit);
    }

    public Integer getPage() {
        return page;
    }

    private void setPage(Integer page) {
        this.page = (page == 0) ? 1: page;
    }

    private Integer getPageLimit() {
        return pageLimit;
    }

    private void setPageLimit(Integer pageLimits) {
        this.pageLimit = pageLimits;
    }

    public Integer totalRows(){
        return this.getPage() * this.getPageLimit();
    }

    public Integer currentPage() {
        return (this.getPage() - 1) * this.getPageLimit();
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", pageLimit=" + pageLimit +
                '}';
    }
}
