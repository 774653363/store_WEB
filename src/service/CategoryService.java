package service;

import java.sql.SQLException;
import java.util.List;

import domain.Category;

/**
 * 分类模块
 * service层接口
 * @author lhm
 *
 */
public interface CategoryService {

	List<Category> findAll() throws SQLException;

	String findAllByAjax() throws SQLException;

	Category findByCid(String cid)throws SQLException;

	void save(String cname)throws SQLException;

	void update(String cid, String cname)throws SQLException;

	void del(String cid)throws SQLException;
}
