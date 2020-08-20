package blog.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


            //强转
             HttpServletRequest req = (HttpServletRequest) request;
             //获取请求uri
             String uri = req.getRequestURI();

             //如果与后台管理页面无关的前端显示页面，则放行
            if (uri.contains("/admin/login.html")){
                chain.doFilter(req,response);
            }else {
                //判断是否在登录状态，是则放行
                if (req.getSession().getAttribute("username").equals("admin")) {
                    chain.doFilter(req, response);
                }

            }


    }

    @Override
    public void destroy() {

    }
}
