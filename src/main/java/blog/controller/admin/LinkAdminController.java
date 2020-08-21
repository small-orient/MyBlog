package blog.controller.admin;

import blog.entity.*;
import blog.service.LinkService;
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
@RequestMapping({"admin/link"})
public class LinkAdminController {
    @Resource
    private LinkService linkService;



    //分页方法，查询数据显示
    @RequestMapping("/list")
    public  @ResponseBody Map<String, Object> list(@RequestParam(value = "currentPage",required = false) String currentPageStr,
                @RequestParam(value = "pageSize",required = false) String pageSizeStr

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

            //查询评论数据
            List<Link> linkList = linkService.list(map);

            //再用一个Map保存信息返回前端
            Map<String, Object> jsonMap = new HashMap<>();


            //评论信息总数
            Long total = linkService.getTotal(map);


            jsonMap.put("linkList", linkList);
            jsonMap.put("total", total);
            //用于前端分页
            jsonMap.put("pb", pb);




        return jsonMap;

    }


    //储存友情链接
    @RequestMapping("/save")
    public  @ResponseBody ResultInfo save(
            @RequestParam(value = "linkName") String linkName,
            @RequestParam(value = "linkUrl") String linkUrl,
            @RequestParam(value = "orderNo") int orderNo){

            //封装数据
            Link link = new Link();
            if (linkName != "" && linkName.length() > 0) {
                link.setLinkName(linkName);
            }

            if (linkUrl != "" && linkUrl.length() > 0) {
                link.setLinkUrl(linkUrl);
            }

            if (orderNo > 0) {
                link.setOrderNo(orderNo);
            }


            //封装结果信息
            ResultInfo info = new ResultInfo();
            //调用services层保存
            Integer count = linkService.add(link);
            if (count > 0) {
                //保存成功
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorInfo("保存失败，请重试！");
            }



        return info;
    }

    //修改友情链接
    @RequestMapping("/update")
    public @ResponseBody Integer update(
            @RequestParam(value ="linkId") int id,
            @RequestParam(value ="linkName") String linkName,
            @RequestParam(value = "linkUrl") String linkUrl,
            @RequestParam(value = "orderNo") int orderNo
    ){



            Integer count = 0;//记录返回数

            Link linkList = linkService.findById(id);
            //给个默认值，如果传过来没有值的话
            if (linkName == null || linkName.length() <= 0) {
                linkName = linkList.getLinkName();

            }

            //给个默认值，如果传过来没有值的话
            if (linkUrl == null || linkUrl.length() <= 0) {
                linkUrl = linkList.getLinkUrl();

            }

            if (orderNo <= 0) {
                orderNo = linkList.getOrderNo();
            }

            count = linkService.update(id, linkName, linkUrl, orderNo);

        /*System.out.println(id);
        System.out.println(linkName);
        System.out.println(linkUrl);
        System.out.println(orderNo);
        System.out.println(count);*/



        return count;
    }

    //回显方法
    @RequestMapping("/relook")
    public @ResponseBody Link relook(
            @RequestParam(value ="linkId") int id
    ){


            //根据前台Id查询文章数据回显至输入框
            Link link = linkService.findById(id);


        return link;
    }


    //删除方法
    @RequestMapping("/delete")
    public @ResponseBody ResultInfo delete(
            @RequestParam(value ="ids[]") Integer[] ids) {

            //定义返回提示信息
            ResultInfo info = new ResultInfo();

            for (Integer id : ids) {
                Integer count = linkService.delete(id);
                if (count > 0) {
                    info.setFlag(true);
                } else {
                    info.setFlag(false);
                }
            }


        return info;
    }




}
