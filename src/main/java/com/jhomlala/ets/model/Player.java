package com.jhomlala.ets.model;



public class Player {
	
	private int id;
	private String name;
	private String joinDate;
	private Long steamID64;
	private String groupName;
	private int groupID;
	private boolean isGameAdmin;
	private boolean showDetailedOnWebMaps;
	private String steamName;
	private String lastLogOff;
	private String avatarURL;
	private String playtime;
	private String personaState;
	

	public String getPersonaState() {
		return personaState;
	}
	public void setPersonaState(String personaState) {
		this.personaState = personaState;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public Long getSteamID64() {
		return steamID64;
	}
	public void setSteamID64(Long steamID64) {
		this.steamID64 = steamID64;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public boolean isGameAdmin() {
		return isGameAdmin;
	}
	public void setGameAdmin(boolean isGameAdmin) {
		this.isGameAdmin = isGameAdmin;
	}
	public boolean isShowDetailedOnWebMaps() {
		return showDetailedOnWebMaps;
	}
	public void setShowDetailedOnWebMaps(boolean showDetailedOnWebMaps) {
		this.showDetailedOnWebMaps = showDetailedOnWebMaps;
	}
	
	
	public String getSteamName() {
		return steamName;
	}
	public void setSteamName(String steamName) {
		this.steamName = steamName; 
	}
	public String getLastLogOff() {
		return lastLogOff;
	}
	public void setLastLogOff(String lastLogOff) {
		this.lastLogOff = lastLogOff;
	}
	public String getAvatarURL() {
		return avatarURL;
	}
	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}
	public String getPlaytime() {
		return playtime;
	}
	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}
	public Player ()
	{
		
	}
	
}