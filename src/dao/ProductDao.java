package dao;

import java.sql.SQLException;
import java.util.List;

import domain.Product;

/**
 * 商品模块Dao接口
 * @author lhm
 *
 */
public interface ProductDao {

	List<Product> findByHot()  throws SQLException;

	List<Product> findByNew()  throws SQLException;

	Product findById(String pid)throws SQLException;

	int findTotalRecordByCid(String cid)throws SQLException;

	List<Product> findAllByCid(String cid, int startIndex, int pageSize)throws SQLException;

	List<Product> findByHistory(String value)throws SQLException;

	void save(Product product)throws SQLException;

	int findTotalRecord(String pflag)throws SQLException;

	List<Product> findAll(int startIndex, int pageSize, String pflag)throws SQLException;

	void update(Product product)throws SQLException;

	void pullOffAndPutOn(String pid, String pflag)throws SQLException;

}
