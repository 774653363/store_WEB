package domain;
/**
 * 购物项的实体,同于封装某一类商品的购买情况
 * @author lhm
 *
 */
public class CartItem {
private Product product;
private int count;
private double subtotal;
public CartItem(Product product,int count) {
	this.product = product;
	this.count = count;
	this.subtotal = product.getShop_price()*count;
}
public Product getProduct() {
	return product;
}
public void setProduct(Product product) {
	this.product = product;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public double getSubtotal() {
	return product.getShop_price()*count;
}
public void setSubtotal(double subtotal) {
	this.subtotal = subtotal;
}

}
