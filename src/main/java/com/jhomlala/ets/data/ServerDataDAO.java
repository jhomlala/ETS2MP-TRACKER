package com.jhomlala.ets.data;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.jhomlala.ets.config.Config;
import com.jhomlala.ets.controllers.APIReader;
import com.jhomlala.ets.model.Player;
import com.jhomlala.ets.model.PlayerStats;
import com.jhomlala.ets.model.ProfileVisit;
import com.jhomlala.ets.model.Server;
import com.jhomlala.ets.model.ServerData;

 
public class ServerDataDAO implements ServerDataInterface 
{
	private Config config;
	private Connection conn;
	private Statement stat;
	
	
	public ServerDataDAO()
	{
		config = new Config();
	}
	
	
	
	
	public boolean insertServerData(Server server) 
	{
		ServerData serverData = new ServerData();
		long time = System.currentTimeMillis();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
		serverData.setTimestamp(timestamp);
		serverData.setServer(server);
		String sql = "INSERT INTO serverdata " +
				"(SERVERDATAID, SERVERDATE, ID, IP, PORT, NAME, SHORTNAME, ONLINE, PLAYERS, MAXPLAYERS) VALUES "
				+ "(?, ?, ? , ?, ?, ?, ?, ?, ?, ?)";
		
 
		try {
			connect();
			PreparedStatement ps = conn.prepareStatement(sql);
		
			ps.setString(1,null);
			ps.setTimestamp(2,serverData.getTimestamp());
			ps.setInt(3,serverData.getServer().getId());
			ps.setString(4,serverData.getServer().getIp());
			ps.setInt(5,serverData.getServer().getPort());
			ps.setString(6,serverData.getServer().getName());
			ps.setString(7,serverData.getServer().getShortname());
			ps.setBoolean(8, serverData.getServer().isOnline());
			ps.setInt(9,serverData.getServer().getPlayers());
			ps.setInt(10,serverData.getServer().getMaxplayers());
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			disconnect();
			throw new RuntimeException(e);
			
		}
		finally {
			disconnect();
			
		}
		
		return true;
	}

	public List<ServerData> getServerDataForServer(int id) 
	{
		
		String sql = "SELECT * FROM serverdata WHERE ID = ? AND SERVERDATE BETWEEN ? AND ? ";
		 
		
		List <ServerData> listServerData = new ArrayList <ServerData>();
		try {
			connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			Calendar c = Calendar.getInstance(); 
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ps.setString(3,dateFormat.format(c.getTime()));
			c.add(Calendar.DATE, -1);
			ps.setString(2,dateFormat.format(c.getTime()));
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) 
			{
				ServerData serverData = new ServerData();
				serverData.setTimestamp(rs.getTimestamp("SERVERDATE"));
				Server server = new Server();
				server.setId(rs.getInt("ID"));
				server.setIp(rs.getString("IP"));
				server.setPort(rs.getInt("PORT"));
				server.setName(rs.getString("NAME"));
				server.setShortname(rs.getString("SHORTNAME"));
				if (rs.getInt("ONLINE")==0)
					server.setOnline(false);
				else
					server.setOnline(true);
				server.setPlayers(rs.getInt("PLAYERS"));
				server.setMaxplayers(rs.getInt("MAXPLAYERS"));
				
				serverData.setServer(server);
				listServerData.add(serverData);
				
			}
			rs.close();
			ps.close();
			disconnect();
			return listServerData;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			disconnect();
			
		}
		
	}

	public List<List<ServerData>> getServersData() 
	{
		// TODO Auto-generated method stub
		return null;
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
