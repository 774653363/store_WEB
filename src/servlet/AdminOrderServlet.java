package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;
import domain.Order;
import domain.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import service.OrderService;
import serviceImp.OrderServiceImp;

public class AdminOrderServlet extends BaseServlet {
	/**
	 * 查询所有订单
	 * 或根据订单状态查询订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//获取参数
	String value = req.getParameter("state"); 
	int pageNumber = 1;
	int pageSize = 10;
	try {
		pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
	} catch (Exception e) {
		// TODO: handle exception
	}
	try {
		//调用service
		OrderService orderService = new OrderServiceImp();
		//声明列表
		PageBean<Order> page = null;
		if(null==value){
			page = orderService.findAll(pageNumber,pageSize);
		}else{
			int state = Integer.parseInt(value);
			page = orderService.findByState(pageNumber,pageSize,state);
		}
		System.out.println(page.getData());
		//将数据存入req
		req.setAttribute("page", page);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "/admin/order/list.jsp";
}
/**ajax
 * 查询订单详情
 */
public String findDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
		String oid = req.getParameter("oid");
		//调用Service获取json
		OrderService orderService = new OrderServiceImp();
		Order order = orderService.findByOid(oid);
		//防止自包含
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray jsonArray = JSONArray.fromObject(order.getOrderItems(),config);
		System.out.println(jsonArray.toString());
		//返回数据
		resp.getWriter().write(jsonArray.toString());
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
}
