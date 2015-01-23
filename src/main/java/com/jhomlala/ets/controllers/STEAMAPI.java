package com.jhomlala.ets.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "steamAPI")

public class STEAMAPI {
 
	private APIReader api;
	private boolean value;
	
	@PostConstruct
	private void init()
	{
		api = new APIReader();
		value = api.isSteamAPI();
	}

	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
 

}