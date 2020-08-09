package blog.service.impl;

import blog.dao.BlogDao;
import blog.entity.Blog;
import blog.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    //根据博客外键文章类型Id查询单条博客信息 *
    public Integer findByArticleTypeId(Integer articleTypeId){
        return blogDao.findByArticleTypeId(articleTypeId);
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
    public Integer delete(Integer[] ids) {
        //复制博客文章类型代码即可
        Integer count = 0;
        Integer resultInt = 0; //返回数据
        for (Integer id :ids){
            count = blogDao.delete(id);
            //每删除一个就判断一次是否删除成功
            if (count > 0){
                //定义返回数据
                resultInt = 1;
            }else {
                resultInt = 0;
            }
        }


        return resultInt;
    }
}
