package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
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

public class AdminProductServlet extends BaseServlet{
	/**
	 * 分页查询商品
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//提供pageBean的默认值
		int pageSize = 20;
		int pageNumber = 1;
		String pflag=req.getParameter("pflag");
		if(null==pflag){
			pflag="0";
		}
		//从req获取pflag
		try {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		} catch (Exception e) {
		}
		//从req中获取pageNumber值
		try {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		} catch (Exception e) {
		}
		try {
			//调用Service服务
			ProductService productService = new ProductServiceImp();
			PageBean<Product> page = productService.findAll(pageNumber,pageSize,pflag);
			//存入req中
			req.setAttribute("product_page", page);
			//页面跳转
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(pflag.equals("0")){
			return "/admin/product/list.jsp";
		}
		if(pflag.equals("1")){
			return "/admin/product/pushDown_list.jsp";
		}
		return null;
	}
	/**
	 * 跳转到商品添加页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
public String addUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
		//先调用CategoryService查询所有分类
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> list = categoryService.findAll();
		//保存到req中
		req.setAttribute("list", list);
	} catch (Exception e) {
		e.printStackTrace();
	}
	//页面跳转
	return "/admin/product/add.jsp";
}

public String edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
		String pid = req.getParameter("pid");
		ProductService productService = new ProductServiceImp();
		Product product = productService.findById(pid);
		req.setAttribute("p", product);
		//先调用CategoryService查询所有分类
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> list = categoryService.findAll();
		//保存到req中
		req.setAttribute("list", list);
	} catch (Exception e) {
	}
	return "/admin/product/edit.jsp";
}
/**
 * 上下架商品
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 */
public String pullOffAndPutOn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
		//获取参数
		String pid = req.getParameter("pid");
		String pflag = req.getParameter("pflag");
		//调用service上下架商品
		ProductService productService = new ProductServiceImp();
		productService.pullOffAndPutOn(pid,pflag);
		if(pflag.equals("0")){
			//跳转页面
			return "/AdminProductServlet?method=findAll&pflag=1";
		}
		if(pflag.equals("1")){
			//跳转页面
			return "/AdminProductServlet?method=findAll&pflag=0";
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return null;
}
}
