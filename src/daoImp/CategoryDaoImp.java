package daoImp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import dao.CategoryDao;
import domain.Category;
import utils.JDBCUtils;
/**
 * 分类模块
 * Dao层实现
 * @author lhm
 *
 */
public class CategoryDaoImp implements CategoryDao {

	/**
	 * 查询所有分类
	 */
	@Override
	public List<Category> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		List<Category> list = qr.query(sql, new BeanListHandler<Category>(Category.class));
		return list;
	}

	@Override
	public Category findById(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category where cid = ?";
		return qr.query(sql, new BeanHandler<>(Category.class),cid);
	}

	@Override
	public void save(String cname) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into category values(null,?)";
		qr.update(sql,cname);
	}

	@Override
	public void update(String cid, String cname) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category set cname = ? where cid = ?";
		qr.update(sql,cname,cid);
	}

	@Override
	public void del(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "delete from category where cid = ?";
		qr.update(sql,cid);
	}

}
