package blog.service.impl;

import blog.dao.CommentDao;
import blog.entity.Comment;
import blog.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;

    @Override
    public int add(Comment comment) {
        return commentDao.add(comment);
    }

    @Override
    public int update(Comment comment) {
        return commentDao.update(comment);
    }

    @Override
    public List<Comment> list(Map<String, Object> map) {
        return commentDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }

    @Override
    public Integer delete(Integer[] ids) {

        //复制博客文章类型代码即可
        Integer count = 0;
        Integer resultInt = 0; //返回数据
        for (Integer id :ids){
            count = commentDao.delete(id);
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

    @Override
    public Integer deleteByBlogId(Integer blogId) {
        return commentDao.deleteByBlogId(blogId);
    }
}
