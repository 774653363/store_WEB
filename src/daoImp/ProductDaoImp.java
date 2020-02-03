package daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import dao.ProductDao;
import domain.Category;
import domain.Product;
import utils.JDBCUtils;
/**
 * 商品模块Dao实现类
 * @author lhm
 *
 */
public class ProductDaoImp implements ProductDao {

	/**
	 * 查询热门商品
	 */
	@Override
	public List<Product> findByHot() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where is_hot = ? and pflag = ? order by pdate desc limit ?";
		Object []objs = new Object[]{1,0,9};
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class),objs);
		return list;
	}

	@Override
	public List<Product> findByNew() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc limit ?";
		Object []objs = new Object[]{0,9};
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class),objs);
		return list;
	}

	/**
	 * 查询最新商品
	 */
	@Override
	public Product findById(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where pid = ?";
//		Product product = qr.query(sql, new BeanHandler<Product>(Product.class),pid);
		List<Map<String, Object>> lists = qr.query(sql, new MapListHandler(),pid);
		Product returnProduct = null;
		try {
			for(Map<String , Object>map:lists){
				Product product = new Product();
				BeanUtils.populate(product, map);
				Category category = new Category();
				BeanUtils.populate(category, map);
				product.setCategory(category);
				returnProduct = product;
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnProduct;
	}

	/**
	 * 查询当前分类的商品总数
	 */
	@Override
	public int findTotalRecordByCid(String cid) throws SQLException {
		Long  total = 0l;
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from product where cid = ? and pflag = 0";
		total = (Long)qr.query(sql, new ScalarHandler(),cid);
		return total.intValue();
	}

	/**
	 * 查询当前分类商品分页数据
	 */
	@Override
	public List<Product> findAllByCid(String cid, int startIndex, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where cid = ? and pflag = ? order by pdate desc limit ?,?";
		Object[]objs = new Object[]{cid,0,startIndex,pageSize};
		List<Product>list = qr.query(sql, new BeanListHandler<>(Product.class),objs);
		return list;
	}

	@Override
	public List<Product> findByHistory(String value) throws SQLException {
		String[] strs = value.split("@");
		
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where pid = ?";
		List<Product>list = new ArrayList<>();
		int i = 0;
		for(String str:strs){
			if(i>=7){
				break;
			}
			i++;
			Product product = qr.query(sql, new BeanHandler<>(Product.class),str);
			list.add(product);
		}
		return list;
	}

	@Override
	public void save(Product product) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into product values(null,?,?,?,?,?,?,?,?,?)";
		Object[]objs = new Object[]{
				product.getPname(),product.getMark_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),
				product.getPdesc(),product.getPflag(),product.getCategory().getCid()
		};
		queryRunner.update(sql,objs);
	}

	@Override
	public int findTotalRecord(String pflag) throws SQLException {
		Long  total = 0l;
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from product where pflag=?";
		total = (Long)qr.query(sql, new ScalarHandler(),pflag);
		return total.intValue();
	}

	@Override
	public List<Product> findAll(int startIndex, int pageSize,String pflag) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc limit ?,?";
		Object[]objs = new Object[]{pflag,startIndex,pageSize};
		List<Product>list = qr.query(sql, new BeanListHandler<>(Product.class),objs);
		return list;
	}

	@Override
	public void update(Product product) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update product set pname = ?,mark_price = ?,shop_price = ?,pimage = ?,"
				+    "pdate = ?,is_hot = ?,pdesc = ?,pflag = ?,cid = ? where pid = ?";
		Object[] objs = new Object[]{
				product.getPname(),
				product.getMark_price(),
				product.getShop_price(),
				product.getPimage(),
				product.getPdate(),
				product.getIs_hot(),
				product.getPdesc(),
				product.getPflag(),
				product.getCategory().getCid(),
				product.getPid()
		};
		qr.update(sql,objs);
	}

	@Override
	public void pullOffAndPutOn(String pid, String pflag) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update product set pflag = ? where pid = ?";
		Object[] objs = new Object[]{
			pflag,pid
		};
		qr.update(sql,objs);
	}

}
