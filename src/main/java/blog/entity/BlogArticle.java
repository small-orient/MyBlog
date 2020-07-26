package blog.entity;

import java.io.Serializable;

/**
 * 博客文章类
 */
public class BlogArticle implements Serializable {

    private static final long serialVersionUID = 1L;
    //文章ID
    private Integer id;
    //文章类型名称
    private String typeName;
    //文章序号
    private Integer orderNo;
    //同一文章类型博客数
    private Integer blogCount;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }
}
