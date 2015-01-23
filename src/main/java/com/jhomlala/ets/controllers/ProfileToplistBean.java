package com.jhomlala.ets.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;



import com.jhomlala.ets.data.ProfileVisitDAO;
import com.jhomlala.ets.data.ProfileVisitInterface;
import com.jhomlala.ets.model.*;

@ManagedBean(name = "ProfileToplist")

public class ProfileToplistBean {
 
	
	private ProfileVisitInterface profileVisitDAO;
	private List<ProfileVisit> profileVisitList;
	@PostConstruct
	public void init()
	{
		profileVisitDAO = new ProfileVisitDAO();
		profileVisitList= profileVisitDAO.getTopProfilesVisited();

	}
	
	public List<ProfileVisit> getProfileVisitList() 
	{
		return profileVisitList;
	}
	public void setProfileVisitList(List<ProfileVisit> profileVisitList)
	{
		this.profileVisitList = profileVisitList;
	}
	
	
	



}