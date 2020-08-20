package blog.dao;

import blog.entity.Blog;
import blog.entity.BlogArticle;
import blog.entity.Link;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexDao {

    //无参查询博客文章类别typename
    public List<BlogArticle> findTypeName();

    //根据文章类型typename查询文章
    public BlogArticle findByTypeName(String typeName);

    //根据博客文章Id查询博客信息
    public List<Blog> findBlog(Integer articleTypeId);

    //无参查询博客发布日期
    public List<Blog> findReleaseDate();

    //无参查询友情链接
    public List<Link> findLink();
}
