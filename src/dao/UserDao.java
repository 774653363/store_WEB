package dao;

import java.sql.SQLException;

import domain.User;

/**
 * 用户模块的DAO层接口
 * @author lhm
 *
 */
public interface UserDao {

	void save(User user) throws SQLException;

	User findByCoide(String code) throws SQLException;

	void update(User user) throws SQLException;

	User find(String username, String password) throws SQLException;

	User findByUsername(String username) throws SQLException;

}
