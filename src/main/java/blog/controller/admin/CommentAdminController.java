package blog.controller.admin;

import blog.entity.Blog;
import blog.entity.Comment;
import blog.entity.PageBean;
import blog.entity.ResultInfo;
import blog.service.CommentService;
import blog.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论管理Controller
 */
@Controller
@RequestMapping({"admin/comment"})
public class CommentAdminController {
    @Resource
    private CommentService commentService;



    //分页方法，查询数据显示
    @RequestMapping("/list")
    public  @ResponseBody String list(@RequestParam(value = "currentPage",required = false) String currentPageStr,
                @RequestParam(value = "pageSize",required = false) String pageSizeStr,
                @RequestParam(value = "state",required = false) String state
    ){


        String result = "";


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
        map.put("state", StringUtil.formatLike(state));

        //查询评论数据
        List<Comment> commentList = commentService.list(map);

        //再用一个Map保存信息返回前端
        Map<String, Object> jsonMap = new HashMap<>();


        //评论信息总数
        Long total = commentService.getTotal(map);


        jsonMap.put("commentList", commentList);
        jsonMap.put("total", total);
        //用于前端分页
        jsonMap.put("pb", pb);


        //封装成json返回
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;

    }

    //删除方法
    @RequestMapping("/commentDelete")
    public @ResponseBody String commentDelete(
            @RequestParam(value ="ids[]") Integer[] ids  ) {

        String result = "";


            Integer count = commentService.delete(ids);


            //定义返回提示信息
            ResultInfo info = new ResultInfo();

            if (count == 1) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
            }


            //返回json
            ObjectMapper mapper = new ObjectMapper();

            try {
                result = mapper.writeValueAsString(info);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        return result;
    }

    //审核方法：修改state
    @RequestMapping("/review")
    public @ResponseBody String commentReview(@RequestParam(value ="ids[]") Integer[] ids,
                                              @RequestParam(value = "state") int state
                                              ) {

        String result = "";


            //定义返回提示信息
            ResultInfo info = new ResultInfo();

            //添加数据
            Comment comment = new Comment();
            for (Integer id : ids) {
                comment.setId(id);
                comment.setState(state);
                //修改状态
                int count = commentService.update(comment);

                System.out.println(count);
                if (count > 0) {
                    info.setFlag(true);
                } else {
                    info.setFlag(false);
                }
            }


            //返回json
            ObjectMapper mapper = new ObjectMapper();
            try {
                result = mapper.writeValueAsString(info);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        return result;

    }


}
