package base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{
@Override
protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
	//获取method参数
	String methodName = req.getParameter("method");
	//默认方法名
	if(null==methodName){
		methodName="execute";
	}
	//获取方法对象
		Method method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
	//获取返回值
		Object url = method.invoke(this,req, resp);
	//进行请求转发
		if(null!=url){
			req.getRequestDispatcher((String)url).forward(req, resp);
		}
	} catch (Exception e) {
		//异常处理
		throw new RuntimeException(e);
	}
}
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
}
