package com.enjoyor.mtdap.common.page;

import oracle.sql.CLOB;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 * @author wuhao
 * @ClassName: Page
 * @Description: 分页
 * @email
 * @date 2016年3月14日 上午9:47:54
 */
public class PageEasyUI<T> {

    private int pageSize;
    private int totalPage; // 总页数
    private int total; // 总记录数
    private int currentPage; // 当前页
    private int currentCount; // 当前记录起始索引

    private String sortExp;        //排序列

    private String sortDir;            //排序方向
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
        //String defaultPageSize = ResourcesProperties.getInstance().getResourcesPropreties().get("page.pageSize.default");
        //this.setPageSize(Integer.valueOf(defaultPageSize));
        this.setPageSize(10);
    }


}
