package utils;

import javax.servlet.http.Cookie;

public class CookieUtils {
public static Cookie getCookie(Cookie[] cookies,String name){
	if (name==null||null==cookies) {
		return null;
	}
	for(Cookie cookie:cookies){
		if(cookie.getName().equals(name)){
			return cookie;
		}
	}
	return null;
}
}
