package com.jhomlala.ets.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jhomlala.ets.config.Config;
import com.jhomlala.ets.model.PlayerStats;

public class PlayerStatsDAO implements PlayerStatsInterface
{
	private Config config;
	private Connection conn;
	private Statement stat;
	
	public PlayerStatsDAO()
	{
		config = new Config();
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

	
	
	@Override
	public boolean insertPlayerData(java.sql.Date date, int number) 
	{
		String sql = "SELECT * FROM playerstats WHERE DATE = ? ";

		try {
			connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, date);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
			{
				int count = rs.getInt("COUNT");
				count = count + number;
				sql = "UPDATE playerstats SET COUNT = '"+count+"' WHERE DATE = '"+date+"'";
				stat = conn.createStatement();
				stat.executeUpdate(sql);
				
			}
			else
			{
				
				sql = "INSERT INTO playerstats values (?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1,null);
				ps.setDate(2, date);
				ps.setInt(3, number);
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
	public List<PlayerStats> getPlayerStats() {

	String sql = "SELECT * FROM playerstats WHERE DATE BETWEEN ? AND ? ";
		 
	List <PlayerStats> pss = new ArrayList <PlayerStats>();
		
		try {
			connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			java.util.Date currentDate = new Date();   
			java.sql.Date currentSqlDate = new java.sql.Date(currentDate.getTime());
			ps.setDate(2, currentSqlDate);
			Calendar c = Calendar.getInstance(); 
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			ps.setString(1,dateFormat.format(c.getTime()));
			c.add(Calendar.DATE, -7);
			java.sql.Date currentSqlDate2 = new java.sql.Date(c.getTime().getTime());
			
			ps.setDate(1, currentSqlDate2);
			
			
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next())
			{
				PlayerStats playerStats = new PlayerStats();
				playerStats.setDate(rs.getDate("DATE"));
				playerStats.setCount(rs.getInt("COUNT"));
				pss.add(playerStats);
			}
			
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			disconnect();
			
		}
		
		return pss;
	}

	

}
