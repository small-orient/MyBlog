package blog.controller.admin;

import blog.entity.Blogger;
import blog.entity.ResultInfo;
import blog.service.BloggerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;


/**
 * 博主登录MVC
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

    @Resource
    private BloggerService bloggerService;

    @RequestMapping("/login")
    public @ResponseBody String login(Blogger blogger) {

//      从前端获取的用户名
        String userName = blogger.getUserName();
//      前端获取的密码
        String password = blogger.getPassword();

      /*  System.out.println(userName+password);*/
        //与数据库账号密码验证
        Blogger user = bloggerService.getByUserName(userName, password);

        //定义返回前端结果信息集,后面用注解改善一下耦合**
        ResultInfo info = new ResultInfo();
        //用于将info类型数据写为String，然后返回
        ObjectMapper mapper = new ObjectMapper();
        String resultInfo = "";

        //看user有无信息
        if (user != null){
            //验证成功
            info.setFlag(true);

      /*      request.getSession().setAttribute("user",user);*/
        }else {
            //验证失败
            info.setFlag(false);
            info.setErrorInfo("账号或密码错误！");
        }

        try {
            //将info写成字符串
           resultInfo = mapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        //返回json
        return resultInfo;
    }
}
