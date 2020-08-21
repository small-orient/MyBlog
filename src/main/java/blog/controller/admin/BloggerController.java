package blog.controller.admin;

import blog.entity.Blogger;
import blog.entity.ResultInfo;
import blog.service.BloggerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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
    public @ResponseBody
    ResultInfo login(Blogger blogger, HttpSession session) {

//      从前端获取的用户名
        String userName = blogger.getUserName();
//      前端获取的密码
        String password = blogger.getPassword();

        /*  System.out.println(userName+password);*/
        //与数据库账号密码验证
        Blogger user = bloggerService.getByUserName(userName, password);

        //定义返回前端结果信息集
        ResultInfo info = new ResultInfo();


        //看user有无信息
        if (user != null) {
            //验证成功
            info.setFlag(true);

            //登录则设置用户session
            session.setAttribute("username", userName);
        } else {
            //验证失败
            info.setFlag(false);
            info.setErrorInfo("账号或密码错误！");
        }



        //返回json
        return info;
    }

    //根据密码查询博主
    @RequestMapping("/findBlogger")
    public @ResponseBody
    Blogger findBlogger(@RequestParam(value = "oldPassword") String oldPassword) {


            Blogger blogger = null;
            if (oldPassword != null && oldPassword.length() > 0) {
                blogger = bloggerService.findBlogger(oldPassword);
            }



        return blogger;
    }


    //修改密码
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public @ResponseBody Integer updatePassword(@RequestParam(value = "password") String password) {

            Integer count = 0;
            String result = "";

            if (password != null && password.length() > 0) {
                count = bloggerService.updatePassword(password);
            }



        return count;
    }


    //退出
    @RequestMapping(value = "/logout")
    public @ResponseBody String logOut(HttpServletRequest request) {
        //注销session
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.invalidate();
        //回到登录页面

        return null;
    }
}
