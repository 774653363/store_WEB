package utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import domain.OrderItem;

/**
 * 将数据封装给JavaBean,支持时间类型转换
 * 例如:
 * User user = new User();
 * MyBeanUtils.populate(user,req.getParamaterMap());
 * @author lhm
 *
 */
public class MyBeanUtils {
public static<T> T populate(Class<T> clazz,Map<String,String[]> map){
	try {
		//使用反射创建实例
		T obj = clazz.newInstance();
		//创建BeanUtils提供时间转换器
		DateConverter converter = new DateConverter();
		//设置需要转换的格式
		converter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
		//注册转换器
		ConvertUtils.register(converter, java.util.Date.class);
		//封装数据
		BeanUtils.populate(obj, map);
		return obj;
	} catch (Exception e) {
		throw new RuntimeException(e);
	} 
	
}




}
