package com.guoshuai.mtdap.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import oracle.sql.CLOB;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value = "Page", description = "分页参数")
public class Page<T> implements Serializable {

    /**
     * 
     */
    public static final long serialVersionUID = -2425523433869995250L;

    @ApiModelProperty(value = "每页数量", hidden = true)
    @ApiParam(value = "每页数量", defaultValue = "10")
    public int pageSize = 10;

    @ApiModelProperty(value = "总页数", hidden = true)
    public int totalPage;

    @ApiModelProperty(value = "总记录数", hidden = true)
    public int totalCount;

    @ApiModelProperty(value = "当前页", hidden = true)
    @ApiParam(value = "当前页", defaultValue = "1")
    public int currentPage = 1;

    @ApiModelProperty(value = "当前记录起始索引", hidden = true)
    public int currentCount;

    @ApiModelProperty(value = "排序列", hidden = true)
    public String sortExp;

    @ApiModelProperty(value = "排序方向", hidden = true)
    public String sortDir;

    @ApiModelProperty(value = "额外参数", hidden = true)
    public Map<String, String> mapBean;

    @ApiModelProperty(value = "返回结果", hidden = true)
    public List<T> result;

    public Page() {
        super();
    }

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

    public List<T> getResult() {
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setResult(List<T> result) {
        if (result != null) {
            for (T t : result) {
                if (t instanceof Map) {
                    for (Object key : ((Map) t).keySet()) {
                        if (((Map) t).get(key) instanceof CLOB) {
                            try {
                                CLOB clob = ((CLOB) ((HashMap) t).get(key));
                                ((Map) t).put(key, clob.getSubString(1, (int) clob.length()));
                            } catch (SQLException e) {
                            }
                        }
                    }
                }
            }
        }
        this.result = result;
    }

    public void setCustomResult(List<T> result) {
        this.result = result;
    }

    public int getTotalPage() {
        if (pageSize == 0) {
            return 0;
        }
        if (totalCount % pageSize == 0)
            totalPage = totalCount / pageSize;
        else
            totalPage = totalCount / pageSize + 1;

        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        if (currentPage <= 0)
            currentPage = 1;
        if (getTotalPage() > 0) {
            if (currentPage > getTotalPage())
                currentPage = getTotalPage();
        }
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage > 0) {
            this.currentPage = currentPage;
        }
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
        if (pageSize != 0) {
            this.pageSize = pageSize;
        }
    }

    public void setDefaultPageSize() {
        this.setPageSize(10);
    }

    public Map<String, String> getMapBean() {
        return this.mapBean;
    }

    public void setMapBean(Map<String, String> mapBean) {
        this.mapBean = mapBean;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageEasyUI ConvertToPageEasyUI() {
        PageEasyUI page = new PageEasyUI();
        page.setCurrentCount(this.currentCount);
        page.setCurrentPage(this.currentPage);
        page.setCurrentResult(this.currentCount);
        page.setPageSize(this.pageSize);
        page.setRows(result);
        page.setSortDir(this.sortDir);
        page.setSortExp(this.sortExp);
        page.setTotal(this.totalCount);
        page.setTotalPage(this.totalPage);
        return page;
    }

    public Object convertPageByType(String type) {
        switch (type) {
            case "page":
                return this;
            case "easyui":
                return ConvertToPageEasyUI();
            case "bootstrap":
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rows", this.getResult());
                map.put("total", this.totalCount);
                return map;
            default:
                return this;
        }
    }
}
