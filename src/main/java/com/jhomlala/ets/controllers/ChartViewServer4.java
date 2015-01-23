package com.jhomlala.ets.controllers;

import javax.annotation.PostConstruct;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean; 
 






import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.jhomlala.ets.config.Config;
import com.jhomlala.ets.data.ServerDataDAO;
import com.jhomlala.ets.data.ServerDataInterface;
import com.jhomlala.ets.model.ServerData;
 
@ManagedBean
public class ChartViewServer4 implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LineChartModel areaModel;
    private ServerDataInterface serverDAO;
    private Config config;
    private List<ServerData> serverDataList;
    
    @PostConstruct
    public void init() {
    	serverDAO = new ServerDataDAO();
    	config = new Config();
    	initAreaModel();
        createAreaModel();
        
        
    }
 
    public LineChartModel getAreaModel() {
        return areaModel;
    }
     
    private void initAreaModel()
    {
    	serverDataList = serverDAO.getServerDataForServer(4);
        areaModel = new LineChartModel();
 
        LineChartSeries stats = new LineChartSeries();
        stats.setFill(true);
        stats.setLabel("Server 1");

        for (int index = 0 ; index < serverDataList.size(); index++)
        {
        	
        		if (index % 2 ==0 && index + 1 < serverDataList.size())
        		{
        			
        			int hoursInServerTime = serverDataList.get(index).getTimestamp().getHours();
        			serverDataList.get(index).getTimestamp().setHours(hoursInServerTime+config.getHourToAdd());
        			String time = serverDataList.get(index).getTimestamp().toString();
        			int playersInIndexTime = serverDataList.get(index).getServer().getPlayers();
        			int playersInIndexPlusOneTime = serverDataList.get(index).getServer().getPlayers();
        			stats.set(time,(playersInIndexTime+playersInIndexPlusOneTime)/2);
        		}
        	
        		
        	index++;
        }
        areaModel.addSeries(stats);
    }
    
    
    private void createAreaModel() 
    {

        areaModel.setTitle("Server 1 ("+serverDataList.get(0).getServer().getShortname()+ ")" + serverDataList.get(0).getServer().getIp()+":"+serverDataList.get(0).getServer().getPort());
        areaModel.setLegendPosition("ne");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);
   
        Axis xAxis = new CategoryAxis("Date");
        
        xAxis.setTickAngle(-50);
        xAxis.setMin(0);
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        
        yAxis.setLabel("Players");
       
    }
}