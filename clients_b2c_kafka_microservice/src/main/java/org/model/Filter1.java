package org.model;

public class Filter1 {
    private String filter;
    private String search;
    private Sort sort;
    private User user;
    public Filter1(String filter, String search, Sort sort,User user) {
        this.filter = filter;
        this.search = search;
        this.sort = sort;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
    public Filter1(){}
}
