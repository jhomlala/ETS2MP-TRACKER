package com.jhomlala.ets.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jhomlala.ets.data.PlayerStatsDAO;
import com.jhomlala.ets.data.PlayerStatsInterface;
import com.jhomlala.ets.data.ServerDataDAO;
import com.jhomlala.ets.data.ServerDataInterface;
import com.jhomlala.ets.model.Player;
import com.jhomlala.ets.model.Server;


public class APIReader 
{
	private boolean ets2mpAPI;
	private boolean steamAPI;
	private JSONReader jsonReader;
	private final String key = "E74E497902C0E98907D59F7B7E0353A0";
	private boolean enabledPlayerChecking = true;
	private PlayerStatsInterface playerStatsDAO;
	public APIReader()
	{
		jsonReader = new JSONReader();
		checkEts2mpAPI();
		checkSteamAPI();
	}
	
	private void checkSteamAPI() 
	{
		try 
		{
			//check for example robin walker stats 
			JSONObject players = jsonReader.readJsonFromUrl("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="+key+"&steamids=76561197960435530");
			this.steamAPI = true;
		} 
		catch (IOException e) 
		{
			this.steamAPI = false;
		} catch (JSONException e) 
		{
			this.steamAPI = false;
		}
		
	}
	private void checkEts2mpAPI() 
	{
		try 
		{
			JSONObject players = jsonReader.readJsonFromUrl("http://api.ets2mp.com/player/");
			JSONObject servers = jsonReader.readJsonFromUrl("http://api.ets2mp.com/servers/");
			this.ets2mpAPI = true;
		} 
		catch (IOException e) 
		{
			this.ets2mpAPI = false;
		} catch (JSONException e) 
		{
			this.ets2mpAPI = false;
		}
		
	}

	public boolean isEts2mpAPI() {
		return ets2mpAPI;
	}

	public boolean isSteamAPI() {
		return steamAPI;
	}
	
	public Player getPlayer(long playerID)
	{
		Player player = getETS2MPInforamtions(playerID);
		player = getSteamInformations(player);
		
		return player;
	}
	
	
	
	public Player getETS2MPInforamtions(long playerID)
	{
		JSONObject JSONWithLogs;
		try {
			JSONWithLogs = JSONReader.readJsonFromUrl("http://api.ets2mp.com/player/"+playerID);
			Boolean logsStatus = JSONWithLogs.getBoolean("error");
			if (!logsStatus)
			{
				JSONObject Logs =  JSONWithLogs.getJSONObject("response"); 
				

				int id = Logs.getInt("id"); 
				String name = Logs.getString("name");
				String joinDate = Logs.getString("joinDate");
				Long steamID64 = Logs.getLong("steamID64");
				String groupName = Logs.getString("groupName");
				int groupID = Logs.getInt("groupID");
				JSONObject permissions = Logs.getJSONObject("permissions");
				Boolean isGameAdmin = permissions.getBoolean("isGameAdmin");
				Boolean showDetailedOnWebMaps = permissions.getBoolean("showDetailedOnWebMaps");
				
				Player player = new Player();
				player.setId(id);
				player.setName(name);
				player.setJoinDate(joinDate);
				player.setSteamID64(steamID64);
				player.setGroupName(groupName);
				player.setGroupID(groupID);
				player.setGameAdmin(isGameAdmin);
				player.setShowDetailedOnWebMaps(showDetailedOnWebMaps);
				
				return player;
			}
			else
				return null;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Player getSteamInformations(Player player)
	{
		
		try 
		{
			
			JSONObject logs = jsonReader.readJsonFromUrl("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="+key+"&steamids="+player.getSteamID64());
			JSONObject response =  logs.getJSONObject("response"); 
			JSONArray playerSummaries = response.getJSONArray("players");
			JSONObject playerSummariesObject = playerSummaries.getJSONObject(0);
			player.setAvatarURL(playerSummariesObject.getString("avatarmedium"));
			player.setSteamName(playerSummariesObject.getString("personaname"));
			player.setLastLogOff(getDateFromUnixTimestamp(playerSummariesObject.getLong("lastlogoff")).toString());
			player.setPersonaState(getStateNameFromStateNumber(playerSummariesObject.getInt("personastate")));
			
			
			player.setPlaytime(getTimeStringFromInt(getETS2PlaytimeFromSteam(player.getSteamID64())));
			
		}
		catch (Exception e) 
		 {
			return player;
		 }

		
		return player;
	}
	
	private String getStateNameFromStateNumber(int number)
	{
		if (number == 0)
			return "offline";
		if (number == 1)
			return "online";
		if (number == 2)
			return "busy";
		if (number == 3)
			return "away";
		if (number == 4)
			return "snooze";
		if (number == 5)
			return "looking to trade";
		if (number == 6)
			return "looking to play";
		
		return "unknown";
	}
	
	private String getTimeStringFromInt(int playtime) 
	{
		String timeString = "";
		int hours,minutes,days;
		if (playtime > 0 && playtime<60 )
		{
			timeString = timeString + playtime + " hours";
			return timeString;
		}
		if (playtime>=60 && playtime<1440)
		{
			hours = playtime / 60;
			minutes = playtime - (hours*60);
			timeString = timeString  + hours + " hours " + minutes + " minutes "; 
			return timeString;
		}
		if (playtime>=1440)
		{
			days = playtime/1440;
			hours = (playtime - (days*1440)) / 60;
			minutes = playtime - ((days*1440)+(hours*60));
			timeString = timeString + days + " days " + hours + " hours " + minutes + " minutes "; 
			return timeString;
		}
		
		return null;
		
	}

	private int getETS2PlaytimeFromSteam(long steamID)
	{
		
		try 
		{
			JSONObject logs = jsonReader.readJsonFromUrl("http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key="+key+"&steamid="+steamID);
			JSONObject response =  logs.getJSONObject("response"); 
			JSONArray games = response.getJSONArray("games");
			for (int i=0;i<games.length();i++)
			{
		
				if (games.getJSONObject(i).getInt("appid")== 227300)
				{
					return games.getJSONObject(i).getInt("playtime_forever");
				}
			}

			return -1;
		}
		catch (JSONException e) 
		 {
			return -1;
		 }
		catch (IOException e) 
		{
			return -1;
		}
	}
	
	public Date getDateFromUnixTimestamp(long timestamp)
	{
		Date date = new Date();
		date.setTime(timestamp*1000);
		return date;
	}
	
	public int getLastMemberID(int searchFromID)
	{
		if (enabledPlayerChecking)
		{
			
			enabledPlayerChecking = false;
			if (isEts2mpAPI())
			{
				
				Player player;
				int currentSearchID = searchFromID;
				int lastSavedID = currentSearchID;
				
				while (true)
				{
					player = getETS2MPInforamtions(currentSearchID);
					if (player!=null)
					{
						currentSearchID++;
						lastSavedID = currentSearchID;

						try {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							Date playerDate = format.parse(player.getJoinDate());
							java.sql.Date sqlDate = new java.sql.Date(playerDate.getTime());
							PlayerStatsInterface playerStatsDAO = new PlayerStatsDAO();
							playerStatsDAO.insertPlayerData(sqlDate, 1);

						} catch (ParseException e) {
					
							e.printStackTrace();
						}

					}
					else
					{
						break;
					}
				}
				enabledPlayerChecking = true;
				return lastSavedID;
				
			
		}
		else
		{
			enabledPlayerChecking = true;
			return searchFromID;
		}
	}
		enabledPlayerChecking = true;
		return searchFromID;
		
	}

	
	public List<Server> getServers()
	{
		List <Server> serverList = new ArrayList<Server>();
		if (isEts2mpAPI())
		{
			try
			{
				JSONObject JSONWithLogs = JSONReader.readJsonFromUrl("http://api.ets2mp.com/servers/");
				Boolean logsStatus = JSONWithLogs.getBoolean("error");
				if (!logsStatus)
				{
					JSONArray logs =  JSONWithLogs.getJSONArray("response"); 
					for (int i=0;i<logs.length();i++)
					{
						Server server = new Server();
						JSONObject serverObject = logs.getJSONObject(i);
						server.setId(serverObject.getInt("id"));
						server.setIp(serverObject.getString("ip"));
						server.setMaxplayers(serverObject.getInt("maxplayers"));
						server.setName(serverObject.getString("name"));
						server.setOnline(serverObject.getBoolean("online"));
						server.setPlayers(serverObject.getInt("players"));
						server.setPort(serverObject.getInt("port"));
						server.setShortname(serverObject.getString("shortname"));
						serverList.add(server);
					}
				}
					
			}
			catch (Exception e)
			{
				
			}
		}
		
		return serverList;
	}
	
	
}

