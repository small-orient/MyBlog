package blog.service;

import blog.entity.Comment;

import java.util.List;
import java.util.Map;
//评论管理Service
public interface CommentService {
    //添加单条评论数据*
    public int add(Comment comment);

    //修改单条评论数据*
    public int update(Comment comment);

    //查询评论信息*
    public List<Comment> list(Map<String,Object> map);

    //查询评论信息数量*
    public Long getTotal(Map<String,Object> map);

    //删除单条评论数据*
    public Integer delete(Integer[] ids);

    //根据博客Id删除评论*
    public Integer deleteByBlogId(Integer blogId);
}
