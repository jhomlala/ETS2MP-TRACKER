package com.jhomlala.ets.controllers;


import javax.annotation.PostConstruct;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;

import org.primefaces.model.chart.ChartSeries;

import com.jhomlala.ets.data.PlayerStatsDAO;
import com.jhomlala.ets.data.PlayerStatsInterface;

import com.jhomlala.ets.model.PlayerStats;

@ManagedBean
public class ChartPlayers implements Serializable {

   private BarChartModel barModel;
   private PlayerStatsInterface playerStatsDAO ;

   @PostConstruct
   public void init() {
	   playerStatsDAO = new PlayerStatsDAO();
       createBarModels();
       
   
   }

   public BarChartModel getBarModel() {
       return barModel;
   }
    


   private BarChartModel initBarModel() 
   {
       BarChartModel model = new BarChartModel();
       ChartSeries player = new ChartSeries();
       List <PlayerStats> playerStatsList = playerStatsDAO.getPlayerStats();
       player.setLabel("Players");
       for (PlayerStats pl : playerStatsList)
       {
    	   String time = pl.getDate().toString();
    	   player.set(time,pl.getCount());
       }
      
       model.addSeries(player);
       return model;
   }
    
   private void createBarModels() {
       createBarModel();
   }
    
   private void createBarModel() {
       barModel = initBarModel();
        
       barModel.setTitle("New players in day:");
       barModel.setLegendPosition("ne");
        
       Axis xAxis = barModel.getAxis(AxisType.X);
       xAxis.setLabel("Date");
       xAxis.setMin(0);
       Axis yAxis = barModel.getAxis(AxisType.Y);
       yAxis.setLabel("Players");
       yAxis.setMin(0);
       
   }
    
  

}