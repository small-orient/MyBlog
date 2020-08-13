package blog.controller.admin;

import blog.entity.BlogArticle;
import blog.entity.PageBean;
import blog.entity.ResultInfo;
import blog.service.BlogArticleService;
import blog.service.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


/**
 * 博客文章类别管理Controller
 */
@Controller
@RequestMapping({"/admin/blogArticle"})
public class BlogArticleAdminController {
    //博客文章类型注入
    @Resource
    private BlogArticleService blogArticleService;

    //博客注入
    @Resource
    private BlogService blogService;

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


        //储存文章类型
        @RequestMapping("/save")
        public  @ResponseBody String save(
                    @RequestParam(value = "typeName") String typeName,
                    @RequestParam(value = "orderNo") String orderNo
                    ){

            //封装数据
            BlogArticle blogArticle = new BlogArticle();
            if (typeName != "" && typeName.length() >0){
                blogArticle.setTypeName(typeName);
            }

            int orderNumber = 0;
            if (orderNo != "" && orderNo.length() >0){
                orderNumber = Integer.parseInt(orderNo);
                blogArticle.setOrderNo(orderNumber);
            }



            //封装结果信息
            ResultInfo info = new ResultInfo();
            //调用services层保存
            Integer count = blogArticleService.add(blogArticle);
            if (count != 0){
                //保存成功
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorInfo("保存失败，请重试！");
            }

            //返回json
            ObjectMapper mapper = new ObjectMapper();
            String resultInfo = "";
            try {
                resultInfo = mapper.writeValueAsString(info);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return resultInfo;
        }


        //回显方法
        @RequestMapping("/relook")
        public @ResponseBody String relook(
                @RequestParam(value ="id_update") int id
                ){

            //根据前台Id查询文章数据回显至输入框
            BlogArticle blogArticle = blogArticleService.findById(id);

            //返回json
            ObjectMapper mapper = new ObjectMapper();
            String result = "";
            try {
                result = mapper.writeValueAsString(blogArticle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        }

    //修改文章类型
    @RequestMapping("/update")
    public @ResponseBody String update(
            @RequestParam(value ="id_update") int id,
            @RequestParam(value ="typeName_update") String typeName_update,
            @RequestParam(value ="orderNo_update") String orderNo_update
    ){

        Integer count = 0;//记录返回数

        //给个默认值，如果传过来没有值的话
       if (typeName_update == null || typeName_update.length() <=0){
           BlogArticle blogArticle = blogArticleService.findById(id);
           String typeName = blogArticle.getTypeName();
           typeName_update = typeName;
       }

        if (orderNo_update == null || orderNo_update.length() <=0){
            BlogArticle blogArticle = blogArticleService.findById(id);
            String orderNo= blogArticle.getTypeName();
            orderNo_update = orderNo;
        }

       count = blogArticleService.update(id, typeName_update, orderNo_update);
        //返回json
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result = mapper.writeValueAsString(count);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }


    //删除文章类型
    @RequestMapping("/delete")
    public @ResponseBody String delete(
            @RequestParam(value ="ids[]") Integer[] ids){

        //定义返回提示信息
        ResultInfo info = new ResultInfo();
        Integer blogNum = 0;
        for (Integer id : ids) {

             /*
             Integer count = blogArticleService.delete(ids);
            因为博客文章类型表是博客表的外键，若删除的文章类型下有被某偏博客引用，则不能删除
           */
                //判断根据这个文章类型id是否能查询出博客文章，若能查出来，则提示不能删除
                blogNum = blogService.findByArticleTypeId(id);

                if (blogNum > 0 ) {
                    //"1"表示：该类型被文章引用，不能删除！
                    info.setErrorInfo("1");
                    info.setFlag(false);
                } else {
                    //可以删除该类型
                    Integer deleteCount = blogArticleService.delete(id);
                    if (deleteCount > 0 ){
                        info.setFlag(true);
                    }else {
                        info.setFlag(false);
                    }

                }


            }





        //返回json
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result = mapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    //写博客下拉列表名称显示方法
    @RequestMapping("/typeName")
    public @ResponseBody String typeName(){
        List<BlogArticle> blogArticles = blogArticleService.countList();


        String Article = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Article = mapper.writeValueAsString(blogArticles);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Article;

    }


}
