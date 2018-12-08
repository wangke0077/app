package com.offcn.pojo;

import java.util.List;

public class Page {
    private List<AppInfo> infoList;
    //当前页
    private int currentPageNo;
    //总记录数
    private int totalCount;
    //总页数
    private int totalPageCount;
    //yeshu
    private int pageSize;

    public List<AppInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<AppInfo> infoList) {
        this.infoList = infoList;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
