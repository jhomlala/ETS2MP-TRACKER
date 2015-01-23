package com.jhomlala.ets.model;

public class Server 
{
	private int id;
	private String ip;
	private int port;
	private String name;
	private String shortname;
	private boolean online;
	private int players;
	private int maxplayers;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public int getPlayers() {
		return players;
	}
	public void setPlayers(int players) {
		this.players = players;
	}
	public int getMaxplayers() {
		return maxplayers;
	}
	public void setMaxplayers(int maxplayers) {
		this.maxplayers = maxplayers;
	}
	
	
}
