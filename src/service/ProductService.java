package service;

import java.sql.SQLException;
import java.util.List;

import domain.PageBean;
import domain.Product;

/**
 * 商品模块Service接口
 * @author lhm
 *
 */
public interface ProductService {

	List<Product> findByHot() throws SQLException;

	List<Product> findByNew() throws SQLException;

	Product findById(String pid) throws SQLException;

	PageBean<Product> findByCid(String cid, int pageNumber, int pageSize)throws SQLException;

	List<Product> findByHistory(String value)throws SQLException;

	void save(Product product)throws SQLException;

	PageBean<Product> findAll(int pageNumber, int pageSize, String pflag)throws SQLException;

	void update(Product product)throws SQLException;

	void pullOffAndPutOn(String pid, String pflag)throws SQLException;

}
