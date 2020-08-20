package blog.service.impl;

import blog.dao.IndexDao;
import blog.entity.Blog;
import blog.entity.BlogArticle;
import blog.entity.Link;
import blog.service.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private IndexDao indexDao;

    @Override
    public List<BlogArticle> findTypeName() {
        return indexDao.findTypeName();
    }

    @Override
    public BlogArticle findByTypeName(String typeName) {
        return indexDao.findByTypeName(typeName);
    }

    @Override
    public List<Blog> findBlog(Integer id) {
        return indexDao.findBlog(id);
    }

    @Override
    public List<Blog> findReleaseDate() {
        return indexDao.findReleaseDate();
    }

    @Override
    public List<Link> findLink() {
        return indexDao.findLink();
    }
}
