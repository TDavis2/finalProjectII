/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Data.prices;

/**
 *
 * @author Owner
 */
public class conversions {
    
    private static double btcStart;
    private static double ltcAmt;
    private static double ethAmt;
    private static double btcFinal;
    
    private static prices prices;
    
    public conversions(){
        
        this.prices = new prices();
        this.btcStart = 1; 
        
    }
    
    private void convertbtcToltc(){
        this.ltcAmt = this.btcStart * this.prices.getBTCLTCRate();
    }
    
    private void convertltcToeth(){
        this.ethAmt = this.ltcAmt * this.prices.getLTCETHRate();
    }
    
    private void convertethTobtc(){
        this.btcFinal = this.ethAmt * this.prices.getETHBTCRate();
    }
    
    public void updateAmts(){
        convertbtcToltc();
        convertltcToeth();
        convertethTobtc();
    }
    
    public double getbtcStart(){
        return this.btcStart;
    }
    
    public double getltcAmt(){
        return this.ltcAmt;
    }
    
    public double getethAmt(){
        return this.ethAmt;
    }
    
    public double getbtcFinal(){
        return this.btcFinal;
    }
    
}
