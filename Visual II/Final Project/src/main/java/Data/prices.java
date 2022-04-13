/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import exchanges.binance; 

/**
 *
 * @author Owner
 */
public class prices {
    
    private double ethbtcRate;
    private double btcltcRate; 
    private double ltcethRate;
    
    private static binance binance; 
    
    public prices(){
        
        this.binance = new binance();
        
        updatePrices();
        
    }
    
    public void updatePrices(){
        /////////   REVERSE ENGINEER SPOT RATE  /////////
        double ltcbtcRate = this.binance.getPrice("LTCBTC");
        this.btcltcRate = 1/ltcbtcRate;
        
        this.ltcethRate = this.binance.getPrice("LTCETH");
        this.ethbtcRate = this.binance.getPrice("ETHBTC");
        
         
        
    }
    
    public double getETHBTCRate(){
        return this.ethbtcRate;
    }
    
    public double getBTCLTCRate(){
        return this.btcltcRate;
    }
    
    public double getLTCETHRate(){
        return this.ltcethRate;
    }
    
    
}
