package blog.controller.admin;

import blog.entity.BlogArticle;
import blog.entity.PageBean;
import blog.service.BlogArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 博客文章类别管理Controller
 */
@Controller
@RequestMapping({"/admin/blogArticle"})
public class BlogArticleAdminController {

    @Resource
    private BlogArticleService blogArticleService;

    /**
     * 查询博客类型列表
     * @return
     */
    @RequestMapping("/list")
    public  @ResponseBody String list(
                @RequestParam(value = "currentPage",required = false) String currentPageStr,
                @RequestParam(value = "pageSize",required = false) String pageSizeStr
                ){

        //先判断所得参数是不是空，然后将其转为int数据
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            //如果currentPageStr这个所接收的参数是空,或是没有传递的话，直接默认为第一页；
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            //每页显示条数不传递则默认6条
            pageSize = 6;
        }

        //将分页数据先保存与pagebean中，给Serivce层读取,以便完成start与end初始化进行操作数据库
        PageBean<BlogArticle> pb = new PageBean<BlogArticle>(currentPage,pageSize);
        //调用service层方法查询数据库博客文章数据
        pb = blogArticleService.list(pb);



        //返回json
        ObjectMapper mapper = new ObjectMapper();
        String resultArticleList = "";
        try {
            resultArticleList = mapper.writeValueAsString(pb);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultArticleList;
    }
}