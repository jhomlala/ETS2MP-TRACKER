package com.jhomlala.ets.data;

import java.util.List;

import com.jhomlala.ets.model.ProfileVisit;

public interface ProfileVisitInterface 
{
	public void connect();
	public void disconnect();
	public boolean insertProfileVisit(int profileID);
	public List <ProfileVisit> getTopProfilesVisited();
}
