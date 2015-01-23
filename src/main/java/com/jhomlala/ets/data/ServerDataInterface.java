package com.jhomlala.ets.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.List;

import com.jhomlala.ets.config.Config;
import com.jhomlala.ets.model.Player;
import com.jhomlala.ets.model.PlayerStats;
import com.jhomlala.ets.model.ProfileVisit;
import com.jhomlala.ets.model.Server;
import com.jhomlala.ets.model.ServerData;

public interface ServerDataInterface 
{	
	public void connect();
	public void disconnect();
	public boolean insertServerData(Server server);
	public List <ServerData> getServerDataForServer(int id);
	public List <List<ServerData>> getServersData();

}
