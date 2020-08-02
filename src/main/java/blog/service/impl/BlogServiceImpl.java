package blog.service.impl;

import blog.dao.BlogDao;
import blog.entity.Blog;
import blog.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogDao blogDao;

    @Override
    public List<Blog> countList() {
        return blogDao.countList();
    }

    @Override
    public List<Blog> findList(Map<String, Object> map) {
        return blogDao.findList(map);
    }


    @Override
    public Blog findById(Integer id) {
        return blogDao.findById(id);
    }


    @Override
    public Long getTotal(Map<String, Object> map) {
        return blogDao.getTotal(map);
    }

    @Override
    public Integer add(Blog blog) {
        return blogDao.add(blog);
    }

    @Override
    public Integer update(Blog blog) {
        return blogDao.update(blog);
    }

    @Override
    public Integer delete(Integer id) {
        return blogDao.delete(id);
    }
}
