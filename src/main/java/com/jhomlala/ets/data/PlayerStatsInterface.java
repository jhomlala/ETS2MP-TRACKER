package com.jhomlala.ets.data;

import java.sql.Date;
import java.util.List;

import com.jhomlala.ets.model.PlayerStats;

public interface PlayerStatsInterface 
{
	public void connect();
	public void disconnect();
	public boolean insertPlayerData(Date date,int number);
	public List <PlayerStats> getPlayerStats();
}
