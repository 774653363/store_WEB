package base;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequest extends HttpServletRequestWrapper{
	//是否已经被编码,默认false,没有被编码
	private boolean encoded = false;
	public MyRequest(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取指定名称的第一个参数
	 */
	public String getParameter(String name){
		String []all = getParametervalue(name);
		if(null==all){
			return null;
		}
		return all[0];
	}

	public String[] getParametervalue(String name) {
		Map<String , String []>map = getParameterMap();
		return map.get(name);
	}
	
	public Map<String, String[]> getParameterMap(){
		try {
		//获取原始参数
		Map<String , String []>map = super.getParameterMap();
		//如果已经编码就跳过
		if(!encoded){
			//如果不是get方法跳过
			if("GET".equalsIgnoreCase(super.getMethod())){
				//遍历Map,并遍历数组
				for(String key:map.keySet()){
					String[] values = map.get(key);
					for(String v:values){
						String encoding = super.getCharacterEncoding();
						if(null==encoding){
							encoding="UTF-8";
						}
							v = new String(v.getBytes("ISO-8859-1"), encoding);
							
					}
				}
				//修改标签,表示已经被编码了
				encoded = true;
			}
		}
		return map;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
