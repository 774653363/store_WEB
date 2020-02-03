package serviceImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import dao.OrderDao;
import daoImp.OrderDaoImp;
import domain.Order;
import domain.OrderItem;
import domain.PageBean;
import service.OrderService;
import utils.JDBCUtils;
/**
 * 订单模块service实现类
 * @author lhm
 *
 */
public class OrderServiceImp implements OrderService {
	
	@Override
	public void save(Order order) throws SQLException  {
		//获取connect对象
		Connection con = JDBCUtils.getConnection();
		try {
			OrderDao dao = new OrderDaoImp();
			con.setAutoCommit(false);
//			调用dao保存order数据
			dao.save(con,order);
			for(OrderItem item:order.getOrderItems()){
//				调用dao保存orderItem数据

				dao.save(con,item);
			}
			DbUtils.commitAndCloseQuietly(con);
		} catch (Exception e) {
			DbUtils.rollback(con);
			e.printStackTrace();
		}
	}

	@Override
	public PageBean<Order> findByUid(int uid, int pageNumber, int pageSize) throws SQLException {
		OrderDao dao = new OrderDaoImp();
		int totalRecord = dao.findTotalRecordByUid(uid);
		PageBean<Order> page = new PageBean<>(pageNumber, pageSize, totalRecord);
		List<Order> data = dao.findAllByUid(uid,page.getStartIndex(),page.getPageSize());
		page.setData(data);
		return page;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		OrderDao dao = new OrderDaoImp();
		return dao.findByOid(oid);
	}

	@Override
	public PageBean<Order> findAll(int pageNumber, int pageSize) throws Exception {
		OrderDao dao = new OrderDaoImp();
		int totalRecord = dao.findTotalRecord();
		PageBean<Order>page = new PageBean<>(pageNumber, pageSize, totalRecord);
		List<Order> data = dao.findAll(page);
		page.setData(data);
		return page;
	}

	@Override
	public PageBean<Order> findByState(int pageNumber, int pageSize, int state) throws Exception {
		OrderDao dao = new OrderDaoImp();
		int totalRecord = dao.findTotalRecord(state);
		PageBean<Order>page = new PageBean<>(pageNumber, pageSize, totalRecord);
		List<Order> data = dao.findByState(page,state);
		page.setData(data);
		return page;
	}

}
