package daoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import dao.OrderDao;
import domain.CartItem;
import domain.Order;
import domain.OrderItem;
import domain.PageBean;
import domain.Product;
import utils.JDBCUtils;
import utils.MyBeanUtils;
/**
 * 订单模块Dao实现类
 * @author lhm
 *
 */
public class OrderDaoImp implements OrderDao{

	/**
	 * 保存订单
	 */
	@Override
	public void save(Connection con, Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object []objs = new Object[]{
				order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),
				order.getTelephone(),order.getUser().getUid()
		};
		qr.update(con, sql, objs);
	}

	/**
	 * 保存订单项
	 */
	@Override
	public void save(Connection con, OrderItem item) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object []objs = new Object[]{
				item.getItemid(),item.getCount(),item.getSubtotal(),
				item.getProduct().getPid(),item.getOrder().getOid()
		};
		qr.update(con, sql, objs);
		
	}

	@Override
	public int findTotalRecordByUid(int uid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		Long total = (Long)qr.query(sql, new ScalarHandler(),uid);
		return total.intValue();
	}

	@Override
	public List<Order> findAllByUid(int uid, int startIndex, int pageSize) throws SQLException {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from orders where uid=? order by ordertime desc limit ?,?";
			List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class),uid,startIndex,pageSize);
			for(Order order:list){
				List<OrderItem> ilist = new ArrayList<OrderItem>();
				sql = "select * from orderitem o,product p where oid=? and o.pid=p.pid";
				List<Map<String , Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
				for(Map<String,Object> map:mapList){
					OrderItem orderItem = 	new OrderItem();
					BeanUtils.populate(orderItem, map);
					Product product = new Product();
					BeanUtils.populate(product, map);
					//设置订单
					orderItem.setOrder(order);
					//设置商品
					orderItem.setProduct(product);
					ilist.add(orderItem);
				}
				//将订单项列表放入订单中
				order.setOrderItems(ilist);
				
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class),oid);
		List<OrderItem> ilist = new ArrayList<OrderItem>();
		sql = "select * from orderitem o,product p where oid=? and o.pid=p.pid";
		List<Map<String , Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
		for(Map<String,Object> map:mapList){
		OrderItem orderItem = 	new OrderItem();
		BeanUtils.populate(orderItem, map);
		Product product = new Product();
		BeanUtils.populate(product, map);
		//设置订单
		orderItem.setOrder(order);
		//设置商品
		orderItem.setProduct(product);
		ilist.add(orderItem);
		}
		order.setOrderItems(ilist);
		return order;
	}

	@Override
	public int findTotalRecord() throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders";
		Long count = (Long)qr.query(sql, new ScalarHandler());
		return count.intValue();
	}

	@Override
	public List<Order> findAll(PageBean<Order> page) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders order by ordertime limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Order.class),page.getStartIndex(),page.getPageSize());
	}

	@Override
	public int findTotalRecord(int state) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders where state = ?";
		Long count = (Long)qr.query(sql, new ScalarHandler(),state);
		return count.intValue();
	}

	@Override
	public List<Order> findByState(PageBean<Order> page,int state) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders  where state = ? order by ordertime limit ?,?";
		Object[] objs = new Object[]{
				state,page.getStartIndex(),page.getPageSize()
		};
		return qr.query(sql, new BeanListHandler<>(Order.class),objs);
	}

}
