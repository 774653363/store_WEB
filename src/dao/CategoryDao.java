package dao;

import java.sql.SQLException;
import java.util.List;

import domain.Category;

/**
 * 分类模块
 * dao层接口
 * @author lhm
 *
 */
public interface CategoryDao {

	List<Category> findAll()throws SQLException;

	Category findById(String cid)throws SQLException;

	void save(String cname)throws SQLException;

	void update(String cid, String cname)throws SQLException;

	void del(String cid)throws SQLException;



}
