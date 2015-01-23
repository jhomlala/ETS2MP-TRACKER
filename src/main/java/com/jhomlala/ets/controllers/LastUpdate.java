package com.jhomlala.ets.controllers;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.jhomlala.ets.config.Config;
import com.jhomlala.ets.model.Player;

@ManagedBean(name = "LastUpdate")

public class LastUpdate {
 
	private Config  config;
	private String lastPlayerUpdate;
	private String lastServerUpdate;
	
	@PostConstruct
	private void init()
	{
		config = new Config();
		setLastPlayerUpdate();
		setLastServerUpdate();
	}
	
	public String getLastPlayerUpdate() {
		return lastPlayerUpdate;
	}
	public void setLastPlayerUpdate() 
	{
		Date date = config.getLastPlayerUpdate();
		
		if (date!=null)
		{
			date.setHours(date.getHours()+config.getHourToAdd());
			this.lastPlayerUpdate = date.toString();
		}
		else
			this.lastPlayerUpdate = "n/a";
	}
	public String getLastServerUpdate()
	{
		return lastServerUpdate;
	}
	public void setLastServerUpdate() {
		Date date = config.getLastServerUpdate();
		
		if (date!=null)
		{
			date.setHours(date.getHours()+config.getHourToAdd());
			this.lastServerUpdate = date.toString();
		}
		else
			this.lastServerUpdate = "n/a";
	}


	
	
	
	



}