package blog.controller.admin;

import blog.entity.Blog;
import blog.entity.BlogArticle;
import blog.entity.ResultInfo;
import blog.service.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 博客信息管理
 */
@Controller
@RequestMapping({"admin/blog"})
public class BlogAdminController {
    @Resource
    private BlogService blogService;

    //保存单条博客功能
    @RequestMapping("/save")
    public @ResponseBody String save(
            @Param(value = "title") String title,
            @Param(value = "ArticleTypeId") String ArticleTypeIdStr,
            @Param(value = "content") String content,
            @Param(value = "summary") String summary,
            @Param(value = "keyWord") String keyWord
            ){


        Blog blog = new Blog();

        int ArticleTypeId = 0;
        if (ArticleTypeIdStr != null && ArticleTypeIdStr.length()>0){
            ArticleTypeId = Integer.parseInt(ArticleTypeIdStr);
            BlogArticle blogArticle = blog.getBlogArticle();
            blogArticle.setId(ArticleTypeId);
            blog.setBlogArticle(blogArticle);
        }

        blog.setTitle(title);
        blog.setContent(content);
        blog.setSummary(summary);
        blog.setKeyWord(keyWord);


        Integer count = blogService.add(blog);

        ResultInfo info = new ResultInfo();

        ObjectMapper mapper = new ObjectMapper();

        if (count > 0){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }

        String resultInfo = "";

        try {
           resultInfo = mapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultInfo;
    }
}
