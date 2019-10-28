package com.guoshuai.mtdap.common.page;

import oracle.sql.CLOB;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PageEasyUI<T> {

    private int pageSize;
    private int totalPage;
    private int total; 
    private int currentPage; 
    private int currentCount; 

    private String sortExp;      

    private String sortDir;          
    List<T> rows;

    public String getSortExp() {
        return sortExp;
    }

    public void setSortExp(String sortExp) {
        this.sortExp = sortExp;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public List<T> getRows() {
        return rows;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setRows(List<T> rows) {
        if (rows != null) {
            for (T t : rows) {
                if (t instanceof Map) {
                    for (Object key : ((HashMap) t).keySet()) {
                        if (((HashMap) t).get(key) instanceof CLOB) {
                            try {
                                CLOB clob = ((CLOB) ((HashMap) t).get(key));
                                ((HashMap) t).put(key, clob.getSubString(1, (int) clob.length()));
                            } catch (SQLException e) {
                            }
                        }
                    }
                }
            }
        }
        this.rows = rows;
    }

    public int getTotalPage() {
        if (pageSize == 0) {
            return 0;
        }
        if (total % pageSize == 0)
            totalPage = total / pageSize;
        else
            totalPage = total / pageSize + 1;

        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentPage() {
        if (currentPage <= 0)
            currentPage = 1;
        if (currentPage > getTotalPage())
            currentPage = getTotalPage();
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public int getCurrentResult() {
        currentCount = (getCurrentPage() - 1) * getPageSize();
        if (currentCount < 0)
            currentCount = 0;
        return currentCount;
    }

    public void setCurrentResult(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setDefaultPageSize() {
        this.setPageSize(10);
    }


}
