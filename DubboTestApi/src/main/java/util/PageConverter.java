package util;

import org.springframework.data.domain.PageRequest;

import java.io.Serializable;


public class PageConverter implements Serializable {
    private int pageNumber;
    private int pageSize;

    public PageConverter() {
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PageConverter(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
    public PageRequest getPageRequest(){
        return PageRequest.of(pageNumber,pageSize);
    }
}