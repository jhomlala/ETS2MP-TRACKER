package com.jhomlala.ets.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.jhomlala.ets.config.Config;
import com.jhomlala.ets.model.Player;

@ManagedBean(name = "LastUser")

public class LastUser {
 
	private Config config;
	private Player player;
	private APIReader apiReader;
	
	@PostConstruct
	public void init()
	{
		config = new Config();
		apiReader = new APIReader();
		int playerID = config.getLastID();
		player = apiReader.getPlayer(playerID);
		setPlayer(player);
	}
	public Player getPlayer() 
	{
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	
	



}