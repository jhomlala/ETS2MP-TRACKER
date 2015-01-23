package com.jhomlala.ets.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "ets2mpAPI")

public class ETS2MPAPI {
 
	private APIReader api;
	private boolean value;
	
	@PostConstruct
	private void init()
	{
		api = new APIReader();
		value = api.isEts2mpAPI();
	}

	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
 

}