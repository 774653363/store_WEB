package domain;

import java.util.Date;

public class User {
private int uid;
private String username;
private String password;
private String name;
private String email;
private String telephone;
private Date birthday;
private String sex;
private int state;
private String code;
public User() {
	super();
	// TODO Auto-generated constructor stub
}
public int getUid() {
	return uid;
}
public void setUid(int uid) {
	this.uid = uid;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getTelephone() {
	return telephone;
}
public void setTelephone(String telephone) {
	this.telephone = telephone;
}
public Date getBirthday() {
	return birthday;
}
public void setBirthday(Date birthday) {
	this.birthday = birthday;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
@Override
public String toString() {
	return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", name=" + name + ", email="
			+ email + ", telephone=" + telephone + ", birthday=" + birthday + ", sex=" + sex + ", state=" + state
			+ ", code=" + code + "]";
}




}
