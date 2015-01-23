package com.jhomlala.ets.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jhomlala.ets.config.Config;
import com.jhomlala.ets.controllers.APIReader;
import com.jhomlala.ets.model.Player;
import com.jhomlala.ets.model.ProfileVisit;

public class ProfileVisitDAO  implements ProfileVisitInterface
{

	private Config config;
	private Connection conn;
	private Statement stat;
	
	public ProfileVisitDAO()
	{
		this.config = new Config();
	}
	
	
	@Override
	public boolean insertProfileVisit(int profileID) {
	String sql = "SELECT * FROM profilevisit WHERE ID = ? ";
		 
		
		
		try {
			connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,profileID);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
			{
				int count = rs.getInt("COUNT");
				count = count + 1;
				sql = "UPDATE profilevisit SET COUNT = '"+count+"' WHERE ID = '"+profileID+"'";
				stat = conn.createStatement();
				stat.executeUpdate(sql);
				
			}
			else
			{
				APIReader api = new APIReader();
				Player player = api.getPlayer(profileID);
				
				sql = "INSERT INTO profilevisit values (?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setInt(1,profileID);
				ps.setInt(2,1);
				ps.setString(3,player.getName() );
				ps.execute();
			}
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			disconnect();
			
		}
		
		
		
		return true;
	}




	@Override
	public List<ProfileVisit> getTopProfilesVisited() 
	{
		List <ProfileVisit> profileVisitList = new ArrayList<ProfileVisit>();
		String sql = "SELECT * FROM profilevisit ORDER BY COUNT DESC LIMIT 5; ";
		 
		
		
		try {
			connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("ID");
				int count = rs.getInt("COUNT");
				String name = rs.getString("NAME");
				ProfileVisit profileVisit = new ProfileVisit();
				profileVisit.setCOUNT(count);
				profileVisit.setID(id);
				profileVisit.setNAME(name);
				profileVisitList.add(profileVisit);
			}
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			disconnect();
			
		}
		
		
		return profileVisitList;
	}

	
	
	@Override
	public void connect() 
	{
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			this.conn = DriverManager.getConnection(config.getURL(),config.getLogin(),config.getPassword());
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	}

	@Override
	public void disconnect() {
		try {
			if (conn !=null)
				if (!conn.isClosed())
					conn.close();
			if (stat != null)
				if (!stat.isClosed())
					stat.close();

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
