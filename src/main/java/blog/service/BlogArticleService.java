package blog.service;

import blog.entity.BlogArticle;
import blog.entity.PageBean;

import java.util.List;



public interface BlogArticleService {

    //查询所有博客文章类型列表：无参查询
    public List<BlogArticle> countList();

    //根据Id查询博客文章类型
    public BlogArticle findById(Integer id);

    //根据参数查询博客文章类型列表
    public PageBean<BlogArticle> list(PageBean<BlogArticle> pageBean);

    //根据参数查询博客文章类型总数
    public Long getTotal();

    //添加单条博客文章
    public Integer add(BlogArticle blogArticle);

    //修改单条博客文章
    public Integer update(int id,String typeName,String orderNo);

    //根据Id删除单条博客文章
    public Integer delete(Integer id);

    //根据文章类型查询单条文章信息
    public BlogArticle findByTypeName(String typeName);
}
