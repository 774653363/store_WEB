package daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import dao.UserDao;
import domain.User;
import utils.JDBCUtils;
/**
 * 用户模块的DAO层的实现类
 * @author lhm
 *
 */
public class UserDaoImp implements UserDao {

	@Override
	public void save(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="insert into user values(null,?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUsername(),user.getPassword(),user.getName(),
				user.getEmail(),user.getTelephone(),user.getBirthday(),
				user.getSex(),user.getState(),user.getCode()};
		qr.update(sql, params);
	}

	/**
	 * 通过激活码查找用户
	 */
	@Override
	public User findByCoide(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where code = ?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	/**
	 * 更新用户信息
	 */
	@Override
	public void update(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update user set username = ?,password = ?,name = ?,"
				+ "email = ?,telephone = ?,birthday = ?,"
				+ "sex = ?,state = ?,code = ? where uid = ?";
		Object[] params = new Object[]{
				user.getUsername(),user.getPassword(),user.getName(),
				user.getEmail(),user.getTelephone(),user.getBirthday(),
				user.getSex(),user.getState(),user.getCode(),user.getUid()
				};
		qr.update(sql, params);
	}

	/**
	 * 根据账号和密码查找用户
	 */
	@Override
	public User find(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ?";
		Object[] objects = new Object[]{username,password};
		User user = qr.query(sql, new BeanHandler<User>(User.class), objects);
		return user;
	}

	@Override
	public User findByUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), username);
		return user;
	}

}
