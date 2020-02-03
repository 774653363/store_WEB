package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import domain.Category;
import domain.Product;
import service.ProductService;
import serviceImp.ProductServiceImp;

public class AddProductServlet extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
		//创建磁盘文件项工厂
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		//设置缓存区大小:如果文件大小超过缓存区大小,会产生临时文件
		diskFileItemFactory.setSizeThreshold(2*1024*1024);
		//获得核心解析类ServletFileUpload
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		//解决中文文件名上传问题
		fileUpload.setHeaderEncoding("UTF-8");
		//设置单个文件大小
//		fileUpload.setFileSizeMax(fileSizeMax);
		//设置表单的所有文件项的文件总大小
//		fileUpload.setSizeMax(sizeMax);
		//解析req,返回文件项列表
		List<FileItem> fileItems = fileUpload.parseRequest(req);
		//遍历文件项列表
		String fileName = "";
		//将遍历的值存放到map集合中
		Map<String, String> map = new HashMap<String, String>();
		for(FileItem item:fileItems){
			
			if(item.isFormField()){
				//普通项
				String name = item.getFieldName();
				String value = item.getString("UTF-8");//解决的是普通项的乱码问题
				System.out.println(name+"  "+value);
				map.put(name,value );
			}else{
				//文件上传项
				//获取文件名
				fileName = item.getName();
				System.out.println("文件名:"+fileName);
				//获取文件输入流
				InputStream is = item.getInputStream();
				//获取文件上传路径
				String path = req.getServletContext().getRealPath("/products/upload");
				//获取输出流
				OutputStream os = new FileOutputStream(path+"/"+fileName);
				//复制文件
				IOUtils.copy(is, os);
				
			}
		}
//		创建一个product对象
		Product product = new Product();
		//封装数据
		BeanUtils.populate(product, map);
		product.setPdate(new Date());
		product.setPflag(0);
		product.setPimage("products/upload/"+fileName);
		Category category = new Category();
		category.setCid(map.get("cid"));
		product.setCategory(category);
		//存入数据库
		ProductService productService = new ProductServiceImp();
		productService.save(product);
		//页面跳转
		req.getRequestDispatcher("/AdminProductServlet?method=findAll").forward(req, resp);;
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
