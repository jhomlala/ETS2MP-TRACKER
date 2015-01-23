package com.jhomlala.ets.model;

import java.sql.Date;
import java.sql.Timestamp;

public class ServerData 
{
	private Timestamp timestamp;
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	private Server server;
	
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	
}
