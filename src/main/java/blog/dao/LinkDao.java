package blog.dao;

import blog.entity.Link;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 评论管理Dao
 */
@Repository
public interface LinkDao {


    //根据Id查询单条友情链接信息*
    public Link findById(Integer id);

    //查询友情链接列表*
    public List<Link> list(Map<String,Object> map);

    //查询友情链接数据数量*
    public Long getTotal(Map<String,Object> map);

    //删除单条友情链接数据*
    public Integer delete(Integer id);

    //添加单条友情链接数据*
    public Integer add(Link link);

    //修改单条友情链接数据*
    public Integer update(Link link);
}
