package domain;

import java.util.Date;
import java.util.List;

public class Order {
private String oid;
private Date ordertime;
private Double total;
private Integer state;
private String address;
private String name;
private String telephone;
private User user;
private List<OrderItem> orderItems;

public Order() {
	// TODO Auto-generated constructor stub
}
public String getOid() {
	return oid;
}
public void setOid(String oid) {
	this.oid = oid;
}
public Date getOrdertime() {
	return ordertime;
}
public void setOrdertime(Date ordertime) {
	this.ordertime = ordertime;
}
public Double getTotal() {
	return total;
}
public void setTotal(Double total) {
	this.total = total;
}
public Integer getState() {
	return state;
}
public void setState(Integer state) {
	this.state = state;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getTelephone() {
	return telephone;
}
public void setTelephone(String telephone) {
	this.telephone = telephone;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public List<OrderItem> getOrderItems() {
	return orderItems;
}
public void setOrderItems(List<OrderItem> orderItems) {
	this.orderItems = orderItems;
}
@Override
public String toString() {
	return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state + ", address="
			+ address + ", name=" + name + ", telephone=" + telephone + ", user=" + user + ", orderItems=" + orderItems
			+ "]";
}


}
