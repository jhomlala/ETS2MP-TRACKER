package com.jhomlala.ets.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.jhomlala.ets.data.ProfileVisitDAO;
import com.jhomlala.ets.data.ProfileVisitInterface;
import com.jhomlala.ets.model.Player;
 
@ManagedBean(name = "PlayerBean")
@SessionScoped
public class PlayerBean {
 
	private String username;
	private Player player;
	private ProfileVisitInterface profileVisitDAO;
 
	
	
	public String getUsername() {
		
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
		
	}
 
	
	public void getPlayerFromAPI()
	{
		APIReader apiReader = new APIReader();
		profileVisitDAO = new ProfileVisitDAO();
		if (username.matches("[0-9]+"))
		{
			player = apiReader.getPlayer(Long.parseLong(username));
			if (player!=null)
				profileVisitDAO.insertProfileVisit(player.getId());
		}
		else
			player = null;
	}

	public Player getPlayer() {
		getPlayerFromAPI();
		return player;
	}
	
	
	
 
}