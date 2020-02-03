package serviceImp;

import java.sql.SQLException;
import java.util.List;

import dao.ProductDao;
import daoImp.ProductDaoImp;
import domain.PageBean;
import domain.Product;
import service.ProductService;
/**
 * 商品模块service实现类
 * @author lhm
 *
 */
public class ProductServiceImp implements ProductService {
	ProductDao dao = new ProductDaoImp();
	/**
	 * 查询热门商品列表
	 */
	@Override
	public List<Product> findByHot() throws SQLException{
		return dao.findByHot();
	}

	/**
	 * 查询最新商品列表
	 */
	@Override
	public List<Product> findByNew() throws SQLException{
		return dao.findByNew();
	}

	/**
	 * 根据id查询商品
	 */
	@Override
	public Product findById(String pid) throws SQLException {
		return dao.findById(pid);
	}

	/**
	 *分页 查询商品
	 */
	@Override
	public PageBean<Product> findByCid(String cid, int pageNumber, int pageSize) throws SQLException {
		//从数据库中获取总记录数
		int totalRecord = dao.findTotalRecordByCid(cid);
		//封装数据
		PageBean<Product>page = new PageBean<>(pageNumber, pageSize, totalRecord);
		//获取分页数据
		List<Product> data = dao.findAllByCid(cid,page.getStartIndex(),page.getPageSize());
		page.setData(data);
		return page;
	}

	@Override
	public List<Product> findByHistory(String value) throws SQLException {
		
		return dao.findByHistory(value);
	}

	
	@Override
	public void save(Product product) throws SQLException {
		dao.save(product);
	}
	/**
	 * 分页查询所有商品
	 */
	@Override
	public PageBean<Product> findAll(int pageNumber, int pageSize,String pflag) throws SQLException {
		//获取所有商品记录数
		int totalRecord = dao.findTotalRecord(pflag);
		//封装pageBean
		PageBean<Product> page = new PageBean<>(pageNumber, pageSize, totalRecord);
		//获取分页商品数据
		List<Product>list = dao.findAll(page.getStartIndex(), page.getPageSize(),pflag);
		//封装pageBean
		page.setData(list);
		return page;
	}

	@Override
	public void update(Product product) throws SQLException {
		dao.update(product);
	}

	@Override
	public void pullOffAndPutOn(String pid, String pflag) throws SQLException {
		dao.pullOffAndPutOn(pid,pflag);
	}

}
