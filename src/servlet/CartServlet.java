package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import base.BaseServlet;
import domain.Cart;
import domain.Product;
import service.ProductService;
import serviceImp.ProductServiceImp;

public class CartServlet extends BaseServlet {
	/**
	 * 添加购物项到购物车
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
public String addToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
	//获取请求参数
	String pid = req.getParameter("pid");
	int count = Integer.parseInt(req.getParameter("count"));
	//通过id查询商品详情
	ProductService productService = new ProductServiceImp();
	Product product = productService.findById(pid);
	//获取购物车
	Cart cart = getCart(req.getSession());
	//将商品添加到Cart
	cart.addCart(product, count);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//重定向到购物车
	resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
	
	return null;
}
/**
 * 获取购物车
 * @param session
 * @return
 */
private Cart getCart(HttpSession session) {
	Cart cart = (Cart)session.getAttribute("cart");
	//还没有Cart在session
	if(null==cart){
		cart = new Cart();
		session.setAttribute("cart",cart);
	}
	//返回cart
	return cart;
}
/**
 * 清空购物车
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 */
public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//获取购物车
	Cart cart = getCart(req.getSession());
	//清空购物车
	cart.clearCart();
	//重定向到购物车
	resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
	return null;
}

/**
 * 移除购物项
 */
public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//获取参数pid
	String pid = req.getParameter("pid");
	//获取购物车
	Cart cart = getCart(req.getSession());
	//移除购物项
	cart.removeCart(pid);
	//重定向到购物车
	resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
	return null;
}
}
