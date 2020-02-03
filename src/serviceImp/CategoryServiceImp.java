package serviceImp;

import java.sql.SQLException;
import java.util.List;

import dao.CategoryDao;
import daoImp.CategoryDaoImp;
import domain.Category;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;
import service.CategoryService;
import utils.JedisUtils;
/**
 * 分类模块
 * service层实现类
 * @author lhm
 *
 */
public class CategoryServiceImp implements CategoryService {
	private CategoryDao dao = new CategoryDaoImp();
	@Override
	public List<Category> findAll()throws SQLException {
		return dao.findAll();
	}
	@Override
	public String findAllByAjax()throws SQLException {
		String jsonStr = null;
		Jedis j = null;
		String value = null;
		
		try {
			//从redis中获取信息
			//获取Jedis连接
			j = JedisUtils.getJedis();
			//获取数据,判断数据是否为空
			value = j.get("category_list");
			//若不为空,直接返回数据
			if(null!=value){
				return value;
			}
			//为空,则从数据库中取出数据,并放入Redis中
			//调用dao查询所有分类
			List<Category> list = dao.findAll();
			//将查询结果转换成json
			jsonStr = JSONArray.fromObject(list).toString();
			//放入Redis中
			j.set("category_list", jsonStr);
			//将结果响应给服务器
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//释放资源
			JedisUtils.close(j);
		}
		return jsonStr;
	}
	@Override
	public Category findByCid(String cid) throws SQLException {
		return dao.findById(cid);
	}
	@Override
	public void save(String cname) throws SQLException {
		dao.save(cname);
		Jedis jedis = null;
		try {
			//清除redis中的数据
			jedis = JedisUtils.getJedis();
			jedis.del("category_list");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			JedisUtils.close(jedis);
		}
		
	}
	@Override
	public void update(String cid, String cname) throws SQLException {
		dao.update(cid,cname);
		Jedis jedis = null;
		try {
			//清除redis中的数据
			jedis = JedisUtils.getJedis();
			jedis.del("category_list");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			JedisUtils.close(jedis);
		}
	}
	@Override
	public void del(String cid) throws SQLException {
		dao.del(cid);
		Jedis jedis = null;
		try {
			//清除redis中的数据
			jedis = JedisUtils.getJedis();
			jedis.del("category_list");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			JedisUtils.close(jedis);
		}
	}
	

}
