package domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
/**
 * 定义一个购物项的集合属性,用于维护所有的购物项,集合采用Map<String,Product>
 * map.key:商品id
 * map.value:商品信息
 * 采用Map集合,方便通过商品id获取商品信息
 */
	private Map<String, CartItem>map = new HashMap<>();
//	定义购物车中的总计,添加,删除等操作
	private double total;
	//相当于属性cartItem
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	public Map<String, CartItem> getMap() {
		return map;
	}

	public double getTotal() {
		total = 0;
		for(String id:map.keySet()){
			total+=(map.get(id).getSubtotal());
		}
		return total;
	}
	/**
	 * 添加商品到购物车
	 * @param product
	 * @param count
	 */
	public void addCart(Product product,int count){
		if(null==product){
			return ;
		}
		//通过商品id获取购物项
		CartItem cartItem = map.get(String.valueOf(product.getPid()));
		//如果是第一次购买
		if(null==cartItem){
			cartItem = new CartItem(product, count);
			map.put(String.valueOf(product.getPid()), cartItem);
		}
		//不是第一次购买
		else{
			System.out.println(cartItem.getCount());
			cartItem.setCount(cartItem.getCount()+count);
		}
		total +=count*product.getShop_price();
		
	}
	/**
	 * 从购物车中移除购物项
	 */
	public void removeCart(String id){
		CartItem cartItem = map.get(id);
		if(null!=cartItem){
			map.remove(id);
		}
		total-=cartItem.getSubtotal();
	}
	/**
	 * 清空购物车
	 */
	public void clearCart(){
		//将map集合清空
		map.clear();
		//total设置为0
		total=0;
	}
}
