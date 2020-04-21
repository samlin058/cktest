package com.lemon;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix= "user")
public class ymlProperties {
	
	
	private String userName;
	
	
	private String sex;

	
	private int age;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "ymlProperties [userName=" + userName + ", sex=" + sex + ", age=" + age + "]";
	}

	public ymlProperties(String userName, String sex, int age) {
		super();
		this.userName = userName;
		this.sex = sex;
		this.age = age;
	}
	

}
