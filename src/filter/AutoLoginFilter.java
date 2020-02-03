package filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserService;
import serviceImp.UserServiceImp;
import utils.CookieUtils;

/**
 * Servlet Filter implementation class AutoLoginFilter
 */
@WebFilter("*")
public class AutoLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AutoLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			//强转
			HttpServletRequest req = (HttpServletRequest)request;
			HttpServletResponse resp = (HttpServletResponse)response;
			//获得session上的用户信息
			User user = (User) req.getSession().getAttribute("user");
			//session上有用户信息,直接放行
			if(null!=user){
				chain.doFilter(req, resp);
				return;
			}
			//如果访问登陆页面,直接放行
			String servletPath = req.getServletPath();
			if(servletPath.startsWith("/UserServlet")){
				String method = req.getParameter("method");
					if("loginUI".equals(method)){
						chain.doFilter(req, resp);
						return;
					}
			}
			//获取Cookie信息
			Cookie autoLoginCookie = CookieUtils.getCookie(req.getCookies(), "autoLoginCookie");
			//如果没有cookie信息直接放行
			if(null==autoLoginCookie){
				chain.doFilter(req, resp);
				return;
			}else{
				String [] strs = autoLoginCookie.getValue().split("@");
				User u = new User();
				u.setUsername(strs[0]);
				u.setPassword(strs[1]);
				//调用service查找用户
				UserService service = new UserServiceImp();
				u = service.login(u);
				//没有查询到相应用户,放行
				if(null==u){
					chain.doFilter(req, resp);
					return;
				}else{
					//自动登陆成功,把用户信息添加入session中
					req.getSession().setAttribute("user", u);
					chain.doFilter(req, resp);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("自动登陆失败,请忽略!");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
