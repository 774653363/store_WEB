package domain;

import java.util.List;

public class PageBean<T> {
private int pageNumber;//当前页(浏览器传递)
private int pageSize;//每页显示个数(固定值,或浏览器传递)
private int totalRecord;//总记录数(查询数据库)
private int totalPage;//总分页页数
private int startIndex;//开始索引
private List<T> data;//分页数据(数据库查询)
public PageBean(int pageNumber,int pageSize,int totalRecord) {
	this.pageNumber = pageNumber;
	this.pageSize = pageSize;
	this.totalRecord = totalRecord;
	//计算总分页数
	if(totalRecord%pageSize==0){
		this.totalPage = totalRecord/pageSize;
	}else{
		this.totalPage = totalRecord/pageSize + 1;
	}
	//计算开始索引
	this.startIndex = (pageNumber-1)*pageSize;
}
public int getPageNumber() {
	return pageNumber;
}
public void setPageNumber(int pageNumber) {
	this.pageNumber = pageNumber;
}
public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
public int getTotalRecord() {
	return totalRecord;
}
public void setTotalRecord(int totalRecord) {
	this.totalRecord = totalRecord;
}
public int getTotalPage() {
	return totalPage;
}
public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
}
public int getStartIndex() {
	return startIndex;
}
public void setStartIndex(int startIndex) {
	this.startIndex = startIndex;
}
public List<T> getData() {
	return data;
}
public void setData(List<T> data) {
	this.data = data;
}

}
