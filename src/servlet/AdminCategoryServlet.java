package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;
import domain.Category;
import service.CategoryService;
import serviceImp.CategoryServiceImp;

public class AdminCategoryServlet extends BaseServlet{

	/**
	 * 查询所有分类数据并跳转到列表页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
//		调用service服务,查询数据
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> list = categoryService.findAll();
		//存入到req中,完成页面跳转
		req.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}
	/**
	 * 跳转到编辑页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//先获取cid
			String cid = req.getParameter("cid");
			//调用service获得cid对应的category
			CategoryService categoryService = new CategoryServiceImp();
			Category category = categoryService.findByCid(cid);
			//保存到req中
			req.setAttribute("category", category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/category/edit.jsp";
	}
	/**
	 * 跳转到添加页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//先获取cid
			String cid = req.getParameter("cid");
			//调用service获得cid对应的category
			CategoryService categoryService = new CategoryServiceImp();
			Category category = categoryService.findByCid(cid);
			//保存到req中
			req.setAttribute("category", category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/category/add.jsp";
	}
	/**
	 * 添加新分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//获取参数
			String cname = req.getParameter("cname");
			//调用service
			CategoryService categoryService = new CategoryServiceImp();
			categoryService.save(cname);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//跳转页面
		return "/AdminCategoryServlet?method=findAll";
	}
	/**
	 * 根据cid更新数据并跳转到列表页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//获取参数
			String cid = req.getParameter("cid");
			String cname = req.getParameter("cname");
			//调用service更新数据
			CategoryService categoryService = new CategoryServiceImp();
			categoryService.update(cid,cname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//跳转页面
		return "/AdminCategoryServlet?method=findAll";
	}
	
	
	public String delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//获取参数
			String cid = req.getParameter("cid");
			//调用service删除数据
			CategoryService categoryService = new CategoryServiceImp();
			categoryService.del(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//跳转页面
		return "/AdminCategoryServlet?method=findAll";
	}
	
}
