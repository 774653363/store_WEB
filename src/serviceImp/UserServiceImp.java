package serviceImp;

import java.sql.SQLException;

import dao.UserDao;
import daoImp.UserDaoImp;
import domain.User;
import service.UserService;
import utils.MailUtils;
/**
 * 用户模块Service层的实现类
 * @author lhm
 *
 */
public class UserServiceImp implements UserService {
	private UserDao dao = new UserDaoImp();
	/**
	 * 用户模块service层注册功能实现
	 * @throws SQLException 
	 */
	public void register(User user) throws SQLException {
		//调用DAO保存用户
		dao.save(user);
		//发送邮件
		MailUtils.sendMail(user.getEmail(),user.getCode());
		
	}
	/**
	 * 用户模块
	 * 激活账户
	 */
	@Override
	public void activeUser(String code) throws SQLException {
		//通过激活码查询用户
		User user = dao.findByCoide(code);
		//不存在则抛出自定义异常
		if(null==user){
			throw new RuntimeException("用户激活码失效，请从重试或重新发送邮件！");
		}
		//更新数据
		user.setCode(null);
		user.setState(1);
		dao.update(user);
	}
	/**
	 * 用户模块 service登陆
	 */
	@Override
	public User login(User user) throws SQLException {
		
		return dao.find(user.getUsername(),user.getPassword());
	}
	@Override
	public User findByUsername(String username) throws SQLException {
		
		return dao.findByUsername(username);
	}


}
