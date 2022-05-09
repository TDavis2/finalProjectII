/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaperAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Owner
 * 
 */
public class TradeHistory {
    private static TradeHistory instance;
    
    // Used to calculate performance metrics
    private int numTrades;
    private int tradesSent; 
    
    private ArrayList<Trade> tradeList;
    private List<Trade> syncList;
    
    private TradeHistory(){
        this.tradeList = new ArrayList<Trade>();
        this.syncList = Collections.synchronizedList(tradeList);
        
        this.numTrades = 0; 
        this.tradesSent = 0; 
    }
    
    public Trade getTradeNum(int index){
        synchronized(this.syncList){
            return this.syncList.get(index);
        }
    }
    
    public void addTrade(Trade newTrade){
        
        synchronized(this.syncList){
            // this.syncList.add(newTrade);
            this.tradeList.add(newTrade);
            
            this.numTrades++;
        }
        
    }
    
    public static TradeHistory getInstance(){
        if(instance == null)
            instance = new TradeHistory();
        
        return instance; 
    }
    
    public int getTrades(){
        return this.numTrades;
    }
    
    public int getTradesSent(){
        return this.tradesSent;
    }
    
    public void incrementTradesSent(){
        this.tradesSent++;
    }
    
    public void resetAccount(){
        this.tradeList.clear();
        this.numTrades = 0;
        this.tradesSent = 0; 
    }
    
}