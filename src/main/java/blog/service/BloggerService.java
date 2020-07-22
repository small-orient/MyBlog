package blog.service;

import blog.entity.Blogger;



public interface BloggerService {
    public Blogger getByUserName(String userName,String password);
}
