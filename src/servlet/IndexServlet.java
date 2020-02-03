package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;
import domain.Category;
import domain.Product;
import service.CategoryService;
import service.ProductService;
import serviceImp.CategoryServiceImp;
import serviceImp.ProductServiceImp;

public class IndexServlet extends BaseServlet{
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
		ProductService productService = new ProductServiceImp();
		//查询热门商品
		List<Product> hotList;
			hotList = productService.findByHot();
		//查询最新商品
		List<Product> newList = productService.findByNew();
		//存放查询结果
		req.setAttribute("hotList", hotList);
		req.setAttribute("newList", newList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/index.jsp";
	}
}
