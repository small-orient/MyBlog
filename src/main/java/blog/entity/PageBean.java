package blog.entity;

import java.util.List;

/**
 * 封装的翻页对象
 */
public class PageBean<T> {
    //总博客记录数
    private long totalCount;
    //总页数
    private int totalPage;
    //当前页码
    private int currentPage;
    //每页显示条数
    private int pageSize;
    //每页显示的博客文章数据集合
    private List<T> resultList;
    //分页数据库搜索范围标记
    private int start;
    private int end;

    public PageBean() {
    }

    public PageBean(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.start = (currentPage-1) * pageSize;
        this.end = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
