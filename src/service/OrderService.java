package service;

import java.sql.SQLException;

import domain.Order;
import domain.PageBean;

/**
 * 订单模块service接口
 * @author lhm
 *
 */
public interface OrderService {

	void save(Order order)throws SQLException;

	PageBean<Order> findByUid(int uid, int pageNumber, int pageSize)throws SQLException;

	Order findByOid(String oid)throws Exception;

	PageBean<Order> findAll(int pageNumber, int pageSize)throws Exception;

	PageBean<Order> findByState(int pageNumber, int pageSize, int state)throws Exception;

}
