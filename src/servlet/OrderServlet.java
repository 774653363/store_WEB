package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;
import domain.Cart;
import domain.CartItem;
import domain.Order;
import domain.OrderItem;
import domain.PageBean;
import domain.User;
import service.OrderService;
import serviceImp.OrderServiceImp;
import utils.UUIDUtils;

public class OrderServlet extends BaseServlet{
	/**
	 * 保存订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		try {
			//获取当前登录用户
			User user = (User)req.getSession().getAttribute("user");
			if(null==user){
				req.setAttribute("msg", "请登录后进行操作!");
				return "/UserServlet?method=loginUI";
			}
			//获取购物车
			Cart cart = (Cart)req.getSession().getAttribute("cart");
			if(null==cart||cart.getCartItems().size()==0){
				req.setAttribute("msg", "购物车空空如也!");
				return	"/jsp/msg.jsp";
			}
			//生成订单
			Order order = new Order();
			//自动生成id
			order.setOid(UUIDUtils.getUUID());
			//设置订单状态未付款
			order.setState(1);
			//设置下单时间
			order.setOrdertime(new Date());
			//设置总价
			order.setTotal(cart.getTotal());
			//设置用户
			order.setUser(user);
			//遍历购物车数据,创建订单项
			List<OrderItem> orderItems = new ArrayList<>();
			for(CartItem cartItem:cart.getCartItems()){
				OrderItem orderItem = new OrderItem();
				//自动生成id
				orderItem.setItemid(UUIDUtils.getUUID());
				//设置商品数量
				orderItem.setCount(cartItem.getCount());
				//设置商品
				orderItem.setProduct(cartItem.getProduct());
				//设置小计
				orderItem.setSubtotal(cartItem.getSubtotal());
				//设置订单
				orderItem.setOrder(order);
				//添加入列表中
				orderItems.add(orderItem);
			}
			//设置所有的订单项
			order.setOrderItems(orderItems);
			//调用service层服务保存订单
			OrderService orderService = new OrderServiceImp();
			orderService.save(order);
			//清空购物车
			cart.clearCart();
			//保存数据到session并页面跳转
			req.getSession().setAttribute("order", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/order_info.jsp";
	}
	
		/**
		 * 根据用户uid查询该用户所有订单
		 * @param req
		 * @param resp
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public String findByUid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			try {
				//从session中获取用户信息
				User user = (User)req.getSession().getAttribute("user");
//				获取当前页号
				int pageNumber = 1;
				try {
					pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
				} catch (Exception e) {
					// TODO: handle exception
				}
//				页面显示订单数
				int pageSize = 3;//暂定,可从浏览器传过来
				
//				调用service查询对应订单
				OrderService orderService = new OrderServiceImp();
				PageBean<Order> orderPage = orderService.findByUid(user.getUid(),pageNumber,pageSize);
				//保存数据到req
				req.setAttribute("orderPage", orderPage);
				//跳转页面
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "/jsp/order_list.jsp";
		}
		/**
		 * 根据订单oid查询订单详情
		 * @param req
		 * @param resp
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws SQLException 
		 */
		public String findByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			//获取oid
			String oid = req.getParameter("oid");
			//调用service查询订单
			OrderService orderService = new OrderServiceImp();
			Order order = orderService.findByOid(oid);
			//显示数据
			req.setAttribute("order", order);
			return "/jsp/order_info.jsp";
		}
}
