package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;
import domain.Category;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;
import service.CategoryService;
import serviceImp.CategoryServiceImp;
import utils.JedisUtils;

public class CategoryServlet extends BaseServlet{
	/**
	 * ajax查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
	CategoryService service = new CategoryServiceImp();
	String jsonStr;
	jsonStr = service.findAllByAjax();
	resp.getWriter().write(jsonStr);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
	
}
