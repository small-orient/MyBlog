package blog.dao;

import blog.entity.Blogger;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 博主Dao
 */
@Repository
public interface BloggerDao {
    /**
     * 验证用户名密码
     * @param userName
     * @param password
     * @return
     */

    public Blogger getByUserName(@Param("userName") String userName,@Param("password") String password);
}
