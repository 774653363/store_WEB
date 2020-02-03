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

public class UpdateProductServlet extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
		//创建磁盘文件项工厂
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		//设置缓存区大小
		diskFileItemFactory.setSizeThreshold(10*1024*1024);
		//创建核心类
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		//解决中文文件名上传问题
		fileUpload.setHeaderEncoding("UTF-8");
		//解析req
		List<FileItem> fileItems = fileUpload.parseRequest(req);
		//存放普通项
		Map<String, String> map = new HashMap<String, String>();
		//文件名
		String fileName = null;
		//遍历FileItem
		for(FileItem item:fileItems){
			
			if(item.isFormField()){
				//普通项,解决普通项中文乱码问题
				map.put(item.getFieldName(), item.getString("UTF-8"));
			}else{
				fileName = item.getName();
				if(null==fileName||fileName.equals(""))
					continue;
				//上传文件存放路径
				String path = req.getServletContext().getRealPath("/products/upload");
				//创建输出流
				OutputStream os = new FileOutputStream(path+"/"+fileName);
				//获取输入流
				InputStream is = item.getInputStream();
				//复制文件
				IOUtils.copy(is, os);
			}
		}
		//封装数据
		Product product = new Product();
		BeanUtils.populate(product, map);
		product.setPdate(new Date());
		
		if(null==fileName||fileName.equals("")){
			String image = map.get("image");
			product.setPimage(image);
		}
		else{
			product.setPimage("/products/upload/"+fileName);
		}
		Category category = new Category();
		category.setCid(map.get("cid"));
		product.setCategory(category);
		//调用service更新数据
		ProductService productService = new ProductServiceImp();
		productService.update(product);
		//页面跳转
		req.getRequestDispatcher("/AdminProductServlet?method=findAll").forward(req, resp);;
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}
