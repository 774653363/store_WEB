package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domain.Order;
import domain.OrderItem;
import domain.PageBean;

/**
 * 订单模块dao接口
 * @author lhm
 *
 */
public interface OrderDao {

	void save(Connection con, Order order)throws SQLException;

	void save(Connection con, OrderItem item)throws SQLException;

	int findTotalRecordByUid(int uid)throws SQLException;

	List<Order> findAllByUid(int uid, int startIndex, int pageSize)throws SQLException;

	Order findByOid(String oid)throws Exception;

	int findTotalRecord()throws Exception;

	List<Order> findAll(PageBean<Order> page)throws Exception;

	int findTotalRecord(int state)throws Exception;

	List<Order> findByState(PageBean<Order> page,int state)throws Exception;

}
