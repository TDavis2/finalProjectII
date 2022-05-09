/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Data.Conversions;
import Data.Prices;
import PaperAccount.Trade;
import PaperAccount.AccountDetails;
import PaperAccount.TradeHistory;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Owner
 */
public class TradingAlgo implements Runnable{
    
    private boolean running;
    
    private double lot;
    
    private Conversions conversions;
    private Prices prices;
    private AccountDetails account;
    private TradeHistory tradeHistory;
    
    public TradingAlgo(){
        this.running = true; 
        this.conversions = new Conversions();
        this.prices = new Prices();
        this.account = AccountDetails.getInstance();
        this.tradeHistory = TradeHistory.getInstance();
        
        this.lot = 1;
        
//        Trade trade1 = new Trade(1, 1.5);
//        this.tradeHistory.addTrade(trade1);
//        
//        Trade trade2 = new Trade(2, 2.25);
//        this.tradeHistory.addTrade(trade2);
//        
//        Trade trade3 = new Trade(1, .75);
//        this.tradeHistory.addTrade(trade3);
//        
//        Trade trade4 = new Trade(3, 2.5);
//        this.tradeHistory.addTrade(trade4);
//        
//        Trade trade5 = new Trade(1, 1.1);
//        this.tradeHistory.addTrade(trade5);
        
    }
    
    private void executeTrade(double amt){
        System.out.println("!!!!!!!!!!!!!!!!!!      TRADE TAKEN         !!!!!!!!!!!!!!!!!!");
        
        Trade newTrade = new Trade(this.lot, amt);
        this.tradeHistory.addTrade(newTrade); 
        this.account.changeBalance(newTrade.getGain());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TradingAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
       
        while(running){
            
            this.conversions.updateAmts();
            System.out.println(this.conversions.getbtcFinal());

            if(this.conversions.getbtcFinal() > this.conversions.getbtcStart()){
                executeTrade(this.conversions.getbtcFinal());
            }
                
            try {
                // Prevents too many API Calls
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(TradingAlgo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void turnOn(){
        this.running = true;
    }
    
    public void turnOff(){
        this.running = false;
    }
    
    public void setLot(double lot){
        this.lot = lot;
    }
}


