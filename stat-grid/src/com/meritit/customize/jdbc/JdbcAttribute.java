package com.meritit.customize.jdbc;

public class JdbcAttribute {
	
	private String driver;  // 驱动
	private String url;     //数据库连接地址
	private String username;  //用户名
	private String password;  // 密码
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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

}
