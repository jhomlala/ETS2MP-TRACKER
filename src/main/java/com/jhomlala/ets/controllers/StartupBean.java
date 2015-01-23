package com.jhomlala.ets.controllers;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.jhomlala.ets.config.Config;
import com.jhomlala.ets.data.ServerDataDAO;
import com.jhomlala.ets.data.ServerDataInterface;
import com.jhomlala.ets.model.Server;

@ManagedBean(eager=true)
@ApplicationScoped
public class StartupBean {
	private Config config;
	private APIReader apiReader;
	
	
    public StartupBean()
    {
    	config = new Config();
    	apiReader = new APIReader();
    	startUpdateProcess();
    }
    
    private void startUpdateProcess () 
    {
    	Timer timer = new Timer(); 
    	System.out.println();
    	TimerTask tt = new TimerTask(){
    		public void run(){
    			updatePlayerInfo();
    		}
    	};
    	timer.schedule(tt, 1000, 1800000); // every 30 minutes
    	
    	Timer timer2 = new Timer();
    	TimerTask tt2 = new TimerTask(){
    		public void run(){
    			updateServerInfo();
    		}
    	};
    	timer2.schedule(tt2, 1000, 1800000); // every 30 minutes

    }

    private void updatePlayerInfo() 
    {
    	
    	int lastID = apiReader.getLastMemberID(config.getLastID());
    	config.setLastID(lastID);
    	config.setLastPlayerUpdate(new Date());
    }

    private void updateServerInfo()
    {
    	APIReader apiReader = new APIReader();
    	List <Server> serverList = apiReader.getServers();
    	System.out.println("UPDATE UPDATE UPDATE UPDATE");
    	if (serverList.size() != 0)
    		for (Server server: serverList)
    		{ 	
    			ServerDataInterface serverDAO = new ServerDataDAO();
    			serverDAO.insertServerData(server);
    		}
    	config.setLastServerUpdate(new Date());
    }
 
    
    
    
}