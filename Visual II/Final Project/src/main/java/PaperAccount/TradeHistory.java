/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaperAccount;

import java.util.ArrayList;

/**
 *
 * @author Owner
 * 
 */
public class TradeHistory {
    
    // Used to calculate performance metrics
    private int numTrades;
    private int numWins; 
    private double totalGain; 
    
    // Performance Metrics
    private double winRate;
    private double avgTrade; 
    
    private ArrayList<Trade> tradeList;
    
    public TradeHistory(){
        this.tradeList = new ArrayList<Trade>();
        
        this.numTrades = 0; 
        this.numWins = 0;
        this.totalGain = 0; 
        this.winRate = 0; 
        this.avgTrade = 0; 
    }
    
    public int getTrades(){
        return this.numTrades;
    }
    
    public int getWins(){
        return this.numWins;
    }
    
    public double getTotalGain(){
        return this.totalGain;
    }
    
    public void addTrade(Trade newTrade){
        // Add new trade to arraylist
        this.tradeList.add(newTrade);
        
        // Track Overall Metrics 
        this.numTrades++;
        if(newTrade.getGain() > 0)
            this.numWins++;
        this.totalGain += newTrade.getGain(); 
        calculatePerformanceMetrics();
    }
    
    public double getWinRate(){
        return this.winRate;
    }
    
    public double getAvgTrade(){
        return this.avgTrade; 
    }
    
    private void calculatePerformanceMetrics(){
        this.winRate = ((double)this.numWins / this.numTrades) * 100; 
        System.out.println("Num Wins: " + this.numWins);
        System.out.println("Num Trades: " + this.numTrades);
        System.out.println("AVG: " + this.numWins / this.numTrades);
        this.avgTrade = this.totalGain / this.numTrades; 
    }
    
    public void resetAccount(){
        this.tradeList.clear();
        this.numTrades = 0;
        this.numWins = 0;
        this.totalGain = 0;
        this.winRate = 0; 
        this.avgTrade = 0; 
    }
    
}