package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import base.BaseServlet;
import domain.User;
import service.UserService;
import serviceImp.UserServiceImp;
import utils.MyBeanUtils;
import utils.UUIDUtils;

public class UserServlet extends BaseServlet {
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("userServlet findAll");
		return null;
	}

	/**
	 * 跳转到注册页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registerUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
	/**
	 * 跳转到登陆页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	/**
	 * 注册方法
	 * 接受页面提交的参数
	 * 封装到User
	 * 调用service进行注册
	 * 跳转到信息提示页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		//获取数据并封装
		User user = null;
		user = MyBeanUtils.populate(User.class, req.getParameterMap());
		//服务器自动生成部分数据
		user.setCode(UUIDUtils.getUUID64());
		user.setState(0);
		//调用service进行处理
		UserService service = new UserServiceImp();
		service.register(user);
		//成功提示
		req.setAttribute("msg", "注册成功,请邮件激活后登陆!");
		//跳转
		return "/jsp/login.jsp";
	}
	/**
	 * 用户模块,账户激活
	 * 获取激活码
	 * 调用service激活账户
	 * 添加提示信息
	 * 跳转页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 */
	public String active(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
		//获取激活码
		String code = req.getParameter("code");
		//用户激活
		UserService service = new UserServiceImp();
			service.activeUser(code);
		//成功提示
		req.setAttribute("msg", "激活成功,请登录!");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", e.getMessage());
		}
		//跳转页面
		return "/jsp/login.jsp";
	}
	
	public String login(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException{
		//封装数据
		User user = MyBeanUtils.populate(User.class, req.getParameterMap());
		//获取自动登陆和记住账户是否勾选
		String autoLogin = req.getParameter("autoLogin");
		String remenberUsername = req.getParameter("remenberUsername");
		//自动登陆勾选了
		if("on".equals(autoLogin)){
			//勾选了发送cookie
			Cookie autoLoginCookie = new Cookie("autoLoginCookie", user.getUsername()+"@"+user.getPassword());
			autoLoginCookie.setPath("/");
			autoLoginCookie.setMaxAge(60*60*24);
			resp.addCookie(autoLoginCookie);
		}else{
			//没有勾选,删除cookie
			Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
			autoLoginCookie.setPath("/");
			autoLoginCookie.setMaxAge(0);
			resp.addCookie(autoLoginCookie);
		}
		//记住账户勾选了
		if("on".equals(remenberUsername)){
			//勾选了就发送cookie
			Cookie remenberUsernameCookie = new Cookie("remenberUsernameCookie", user.getUsername());
			remenberUsernameCookie.setPath("/");
			remenberUsernameCookie.setMaxAge(60*60*24);
			resp.addCookie(remenberUsernameCookie);
		}else{
			//没有勾选就删除cookie
			Cookie remenberUsernameCookie = new Cookie("remenberUsernameCookie", "");
			remenberUsernameCookie.setPath("/");
			remenberUsernameCookie.setMaxAge(0);
			resp.addCookie(remenberUsernameCookie);
		}
		//调用service进行登陆
		UserService service = new UserServiceImp();
		User loginUser = service.login(user);
		//成功处理
		if(null!=loginUser){
			//保存user到Session
			req.getSession().setAttribute("user", loginUser);
			//重定向到首页
			resp.sendRedirect(req.getContextPath()+"/");
			//不使用BaseServlet的请求转发
			return null;
		}
		//失败处理
		//添加错误信息
		req.setAttribute("msg", "账户或密码不正确!");
		//跳转页面到登陆页面
		return "/jsp/login.jsp";
	}
	
	/**
	 * 用户退出功能
	 * 清除session中的用户信息
	 * 重定向到首页
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException 
	 */
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//清除Session中的user信息
		req.getSession().removeAttribute("user");
		//重定向到首页
		resp.sendRedirect(req.getContextPath()+"/UserServlet?method=loginUI");
		//不使用BaseServlet的请求转发
		return null;
	}
	/**
	 * 检验用户名是否可用
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String checkUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException{
//		获取数据
		String username = req.getParameter("username");
//		调用service,获取对应user
		UserService service = new UserServiceImp();
		User user = service.findByUsername(username);
		if(null==user){
			//账号不存在,可用
			resp.getWriter().write("1");
		}else{
			//账户已存在,不能使用
			resp.getWriter().write("0");
		}
		return null;
	}
}
