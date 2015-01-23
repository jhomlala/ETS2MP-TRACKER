package com.jhomlala.ets.config;

import java.util.Date;

import javax.sql.DataSource;

public class Config 
{

	
	private static Date lastServerUpdate;
	private static Date lastPlayerUpdate;
	
	private String host = "localhost";
	private String port = "3306";
	private String login = "root";
	private String password = "";
	private String dbname = "ets2mp";
	
	private final int hourToAdd = 6;
	private static int lastID = 265628;

	public Date getLastServerUpdate() {
		return lastServerUpdate;
	}

	public void setLastServerUpdate(Date lastServerUpdate) {
		this.lastServerUpdate = lastServerUpdate;
	}

	public Date getLastPlayerUpdate() {
		return lastPlayerUpdate;
	}

	public void setLastPlayerUpdate(Date lastPlayerUpdate) {
		this.lastPlayerUpdate = lastPlayerUpdate;
	}

	public int getLastID() {
		return lastID;
	}

	public void setLastID(int lastID) {
		this.lastID = lastID;
	}

	public int getHourToAdd() {
		return hourToAdd;
	}

	public String getURL()
	{
		return "jdbc:mysql://"+host+":"+port+"/"+dbname;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
