package blog.service;

import blog.entity.Blogger;



public interface BloggerService {
    public Blogger getByUserName(String userName,String password);

    //根据密码查询博主
    public Blogger findBlogger(String oldPassword);

    //修改密码
    public Integer updatePassword(String password);
}
