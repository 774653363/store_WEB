package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;
import domain.Category;
import domain.PageBean;
import domain.Product;
import service.CategoryService;
import service.ProductService;
import serviceImp.CategoryServiceImp;
import serviceImp.ProductServiceImp;
import utils.CookieUtils;

public class ProductServlet extends BaseServlet{
	
	/**
	 * 根据pid查询商品详情
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
public String findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
	//获取pid参数
	String pid = req.getParameter("pid");
	//获取cid参数
	String cid = req.getParameter("cid");
	CategoryService categoryService = new CategoryServiceImp();
	Category category = categoryService.findByCid(cid);
	req.setAttribute("product_category", category);
	//获取cookie
	Cookie[] cookies = req.getCookies();
	Cookie history = CookieUtils.getCookie(cookies, "history");
	//第一次
	String temp;
	if(null==history){
		history = new Cookie("history", pid);
		history.setMaxAge(24*60*60);
		history.setPath("/");
		resp.addCookie(history);
	}else{
		String historyValue = history.getValue();
		String[] historyArray = historyValue.split("@");
		List<String>l = Arrays.asList(historyArray);
		List<String>list = new ArrayList<>();
		list.addAll(l);
		if(historyValue.contains(pid)){
			list.remove(pid);
			list.add(0, pid);
		}else{
			list.add(0, pid);
		}
		historyValue = String.join("@", list);
		history = new Cookie("history", historyValue);
		history.setMaxAge(24*60*60);
		history.setPath("/");
		resp.addCookie(history);
	}
	
	//调用service获取对应product
	ProductService service = new ProductServiceImp();
	Product product;
		product = service.findById(pid);
	if(null==product){
		return null;
	}
	//放入req中
	req.setAttribute("product", product);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "/jsp/product_info.jsp";
}
	/**
	 * 根据cid查询product分页列表
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//获取id参数
			String cid = req.getParameter("cid");
			int pageNumber =  1;
			try {
				pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
			} catch (Exception e) {
			}
			int pageSize = 12;
			//调用service层功能
			ProductService service = new ProductServiceImp();
			PageBean<Product> page = service.findByCid(cid,pageNumber,pageSize);
			req.setAttribute("productPage", page);
			req.setAttribute("cid", cid);
			//获取cookie中的浏览记录
			Cookie[]cookies = req.getCookies();
			Cookie history = CookieUtils.getCookie(cookies, "history");
			if(null!=history){
				List<Product> list = service.findByHistory(history.getValue());
				req.setAttribute("history", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/product_list.jsp";
	}
	/**
	 * 清除浏览记录
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String clearHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie history = new Cookie("history", "");
		history.setMaxAge(0);
		history.setPath("/");
		resp.addCookie(history);
		return null;
	}
}
