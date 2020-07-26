package blog.service.impl;


import blog.dao.BlogArticleDao;
import blog.entity.BlogArticle;
import blog.entity.PageBean;
import blog.service.BlogArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 博客文章类别管理Service实现类
 */
@Service("blogArticleService")
public class BlogArticleServiceImpl implements BlogArticleService {

    @Resource
    private BlogArticleDao blogArticleDao;

    //查询所有博客文章类型列表：无参查询
    @Override
    public List<BlogArticle> countList() {
        return blogArticleDao.countList();
    }

    //根据Id查询博客文章类型
    @Override
    public BlogArticle findById(Integer id) {
        return blogArticleDao.findById(id);
    }

    //根据参数分页查询博客文章类型列表
    @Override
    public PageBean<BlogArticle> list(PageBean<BlogArticle> pageBean) {
        //获取Controller初始化过的start与end
        int start = pageBean.getStart();
        int end = pageBean.getEnd();

        System.out.println(start +"--"+end);
        //根据dao查询到的文章数据返回list
        List<BlogArticle> articleList = blogArticleDao.list(start, end);
        //添加到pagebean中返回
        pageBean.setResultList(articleList);

        //查询总页数
        int pageSize = pageBean.getPageSize();
        Long totalCount = blogArticleDao.getTotal();
        int totalPage =
                (int) (totalCount % pageSize == 0?
                        totalCount / pageSize : (totalCount / pageSize)+1);
        //保存到pageBean
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    //根据参数查询博客文章类型总数
    @Override
    public Long getTotal() {

        return blogArticleDao.getTotal();
    }

    //添加单条博客文章
    @Override
    public Integer add(BlogArticle blogArticle) {
        return blogArticleDao.add(blogArticle);
    }

    //修改单条博客文章
    @Override
    public Integer update(BlogArticle blogArticle) {
        return blogArticleDao.update(blogArticle);
    }

    //根据Id删除单条博客文章
    @Override
    public Integer delete(Integer id) {
        return blogArticleDao.delete(id);
    }
}
