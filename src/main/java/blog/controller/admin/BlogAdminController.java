package blog.controller.admin;

import blog.entity.Blog;
import blog.entity.BlogArticle;
import blog.entity.PageBean;
import blog.entity.ResultInfo;
import blog.service.BlogService;
import blog.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //判断ArticleTypeIdStr是否有数据，有则保存
        if (ArticleTypeIdStr != null && ArticleTypeIdStr.length()>0){
            ArticleTypeId = Integer.parseInt(ArticleTypeIdStr);
            BlogArticle blogArticle = blog.getBlogArticle();
            blogArticle.setId(ArticleTypeId);
            blog.setBlogArticle(blogArticle);
        }
        //封装数据至blog
        blog.setTitle(title);
        blog.setContent(content);
        blog.setSummary(summary);
        blog.setKeyWord(keyWord);

        //调用添加方法
        Integer count = blogService.add(blog);
        //返回信息结果
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

    //博客查询功能
    @RequestMapping("/list")
    public @ResponseBody String list(
            @RequestParam(value = "currentPage",required = false) String currentPageStr,
            @RequestParam(value = "pageSize",required = false) String pageSizeStr,
            @RequestParam(value = "title",required = false) String title
    ){
        //因为和查询文章做的显示数据方法一样，故copy部分代码,而且之前做的PageBean因为是泛型所以还可以继续引用
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


        //将分页数据先保存与pagebean中，获取start与end的值
        PageBean<Blog> pb = new PageBean<Blog>(currentPage,pageSize);
        int start = pb.getStart();
        int end = pb.getEnd();
        //保存至Map集合
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("start",start);
        map.put("end",end);
        map.put("title", StringUtil.formatLike(title));
        //分页查询博客信息
        List<Blog> list = blogService.findList(map);
        //博客信息总数
        Long total = blogService.getTotal(map);
        System.out.println(total);
        //再用一个Map保存这两个信息返回前端
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("list",list);
        jsonMap.put("total",total);
        //用于前端分页
        jsonMap.put("pb",pb);



        //封装成json返回
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
