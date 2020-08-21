package blog.controller.view;


import blog.entity.Blog;
import blog.entity.BlogArticle;
import blog.entity.Link;
import blog.entity.PageBean;
import blog.service.BlogService;
import blog.service.IndexService;
import blog.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 首页
 */
@Controller
@RequestMapping("/view/index")
public class IndexController {

@Resource
private IndexService indexService;

@Resource
private BlogService blogService;

    //网站标题
    @RequestMapping("/title")
    public  @ResponseBody  Map<String,Object> title(){

        Map<String,Object> map = new HashMap<>();
        map.put("title","个人博客");

        return map;
    }


    //热门标签
    @RequestMapping("/findTypeName")
    public  @ResponseBody  Map<String,Object> findTypeName(){
        //查询文章类别信息
        List<BlogArticle> list = indexService.findTypeName();

        Map<String,Object> map = new HashMap<>();
        map.put("list",list);


        return map;
    }


    //显示对应标签数据方法
    @RequestMapping("/findSymbolData")
    public  @ResponseBody  List<Blog> findSymbolData(@RequestParam(value = "typeName") String typeName){

        Integer articleId = 0;
        if (typeName != null && typeName.length() > 0){
            BlogArticle blogArticle = indexService.findByTypeName(typeName);
            articleId = blogArticle.getId();
        }

        List<Blog> blog = indexService.findBlog(articleId);


        return blog;

    }
    //显示友情链接方法
    @RequestMapping("/findLink")
    public  @ResponseBody List<Link> findLink(){
        List<Link> links = indexService.findLink();

        /*ArrayList<String> list = new ArrayList();
        for (Link link : links){
            String linkName = link.getLinkName();
            list.add(linkName);
        }*/

       /*java 8 去重的新特性方法stream()， List<String> linkNameList = list.stream().distinct().collect(Collectors.toList());*/


        return links;

    }

    //博客查询功能
    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list(
            @RequestParam(value = "currentPage",required = false) String currentPageStr,
            @RequestParam(value = "pageSize",required = false) String pageSizeStr,
            @RequestParam(value = "title",required = false) String title

    ){



        //因为和查询文章类型做的显示数据方法一样，故copy部分代码,而且之前做的PageBean因为是泛型所以还可以继续引用
        //先判断所得参数是不是空，然后将其转为int数据
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            //如果currentPageStr这个所接收的参数是空,或是没有传递的话，直接默认为第一页；
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            //每页显示条数不传递则默认6条
            pageSize = 6;
        }


        //将分页数据先保存与pagebean中，获取start与end的值
        PageBean<Blog> pb = new PageBean<Blog>(currentPage, pageSize);
        int start = pb.getStart();
        int end = pb.getEnd();
        //保存至Map集合
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("end", end);

        if (title == null && title.length() < 0 && title == "") {
            title = "*";
            map.put("title", StringUtil.formatLike(title));
        }

        //分页查询博客信息
        List<Blog> list = blogService.findList(map);

        //博客信息总数
        Long total = blogService.getTotal(map);

        int totalPage = (int) (total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1);
        pb.setTotalPage(totalPage);

        //再用一个Map保存这两个信息返回前端
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("list", list);
        jsonMap.put("total", total);
        //用于前端分页
        jsonMap.put("pb", pb);



        return jsonMap;
    }





}
