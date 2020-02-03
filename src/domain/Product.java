package domain;

import java.util.Date;

public class Product {
	private int pid;
	private String pname;
	private double mark_price;
	private double shop_price;
	private String pimage;
	private Date pdate;
	private int is_hot;			//0不是热门,1热门
	private String pdesc;
	private int pflag;			//0未下架,1已下架
	//分类,以面向对象的方式描述商品和分类之间的关系
	//多个商品属于一个分类
	private Category category;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getMark_price() {
		return mark_price;
	}
	public void setMark_price(double mark_price) {
		this.mark_price = mark_price;
	}
	public double getShop_price() {
		return shop_price;
	}
	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	public int getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public int getPflag() {
		return pflag;
	}
	public void setPflag(int pflag) {
		this.pflag = pflag;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Product() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", mark_price=" + mark_price + ", shop_price=" + shop_price
				+ ", pimage=" + pimage + ", pdate=" + pdate + ", is_hot=" + is_hot + ", pdesc=" + pdesc + ", pflag="
				+ pflag + ", category=" + category + "]";
	}
}
