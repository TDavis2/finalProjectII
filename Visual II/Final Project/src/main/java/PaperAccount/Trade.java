/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaperAccount;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author Owner
 */
public class Trade {
    private double startAmt;
    private double finalAmt;
    private double gain;
    private double percentGain;
    private LocalDateTime tradeDate;
    private LocalTime executionSpeed;
    
    public Trade(double start, double end, LocalTime speed){
        this.startAmt = start;
        this.finalAmt = end;
        
        this.gain = end - start; 
        this.percentGain = (this.gain/start) * 100; 
        
        this.tradeDate = LocalDateTime.now();
        this.executionSpeed = speed; 
    }
    
    public double getStart(){
        return this.startAmt;
    }
    
    public double getFinal(){
        return this.finalAmt;
    }
    
    public double getGain(){
        return this.gain;
    }
    
    public double getPercent(){
        return this.percentGain;
    }
    
    public LocalDateTime getDate(){
        return this.tradeDate;
    }
    
    public LocalTime getSpeed(){
        return this.executionSpeed;
    }
}