package domain;

public class OrderItem {
private String itemid;
private Integer count;
private Double subtotal;
private Product product;
private Order order;

public OrderItem() {
	// TODO Auto-generated constructor stub
}
public String getItemid() {
	return itemid;
}
public void setItemid(String itemid) {
	this.itemid = itemid;
}
public Integer getCount() {
	return count;
}
public void setCount(Integer count) {
	this.count = count;
}
public Double getSubtotal() {
	return subtotal;
}
public void setSubtotal(Double subtotal) {
	this.subtotal = subtotal;
}
public Product getProduct() {
	return product;
}
public void setProduct(Product product) {
	this.product = product;
}
public Order getOrder() {
	return order;
}
public void setOrder(Order order) {
	this.order = order;
}

}
