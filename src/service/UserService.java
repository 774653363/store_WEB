package service;

import java.sql.SQLException;

import domain.User;

/**
 * 用户模块的service层接口
 * @author lhm
 *
 */
public interface UserService {
	public void register(User user) throws SQLException;

	public void activeUser(String code) throws SQLException;

	public User login(User user) throws SQLException;

	public User findByUsername(String username)throws SQLException;;
}
