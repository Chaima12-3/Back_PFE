package org.model;

public class Filter {

    private String sity;
    private String size;

    public Filter(String sity, String size) {
        this.sity = sity;
        this.size = size;
    }
    public Filter(){}
    public String getSity() {
        return sity;
    }

    public void setSity(String sity) {
        this.sity = sity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
