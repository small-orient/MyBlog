package blog.service.impl;

import blog.dao.BloggerDao;
import blog.entity.Blogger;
import blog.service.BloggerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {
    @Resource
    private BloggerDao bloggerDao;

    /**
     * Service验证用户名密码方法
     * @param userName
     * @param password
     * @return
     */
    @Override
    public Blogger getByUserName(String userName,String password) {

        return bloggerDao.getByUserName(userName,password);
    }
}
