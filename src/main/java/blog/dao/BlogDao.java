package blog.dao;


import blog.entity.Blog;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 *
 * 博客文章类Dao
 */
@Repository
public interface BlogDao {


        //查询所有博客列表：无参查询，供首页使用 *
        public List<Blog> countList();

        //带参数查询博客列表 *
        public List<Blog> findList(Map<String,Object> map);



        //根据博客主键Id查询单条博客信息 *
        public Blog findById(Integer id);

        //根据博客外键文章类型Id查询单条博客信息 *
        public Integer findByArticleTypeId(Integer articleTypeId);


        //带参数查询博客总数 *
        public Long getTotal(Map<String,Object> map);

        //添加单条博客 *
        public Integer add(Blog blog);

        //修改单条博客 *
        public Integer update(Blog blog);

        //根据博客主键Id删除单条博客 *
        public Integer delete(Integer id);


    }

