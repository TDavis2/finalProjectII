/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaperAccount;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Tyler Davis
 */
public class Trade {
    
    // For sending trade data through Json
    public static class TradeData{
        private double startAmt;
        private double finalAmt;
        private String tradeDate;
        private String tradeTime; 
    }
    
    private double startAmt;
    private double finalAmt;
    private double gain;
    private double percentGain;
    private String tradeDate;
    private String tradeTime;
    
    // Constructor for making a new trade in the server
    public Trade(double start, double end){
        this.startAmt = start;
        this.finalAmt = end;
        
        LocalDateTime date = LocalDateTime.now();
        this.tradeDate = date.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        
        LocalTime time = LocalTime.now();
        this.tradeTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss a"));
        System.out.println(this.tradeTime);
    }
    
    // Constructor for receiving trade data from the server
    public Trade(TradeData data){
        
        this.startAmt = data.startAmt;
        this.finalAmt = data.finalAmt;
        this.gain = data.finalAmt - data.startAmt;
        this.percentGain = (this.gain / data.startAmt) * 100;
        this.tradeDate = data.tradeDate;
        this.tradeTime = data.tradeTime; 
        
    }
    
    // Formats a trade for Json to be sent from the server to the client
    public String toJSON(){
        
        StringBuilder json = new StringBuilder();
        
        json.append('{');
        
        json.append("\"startAmt\":");
        json.append(this.startAmt);
        json.append(",");
        
        json.append("\"finalAmt\":");
        json.append(this.finalAmt);
        json.append(",");
        
        json.append("\"tradeDate\":");
        json.append(this.tradeDate);
        json.append(",");
            
        json.append("\"tradeTime\":\"");
        json.append(this.tradeTime);
        json.append("\"");
        
        json.append('}');
        
        return json.toString();
    }
    
    public double getStart(){
        return this.startAmt;
    }
    
    public void setStart(double val){
        this.startAmt = val;
    }
    
    public double getFinal(){
        return this.finalAmt;
    }
    
    public void setFinal(double val){
        this.finalAmt = val;
    }
    
    public double getGain(){
        return this.gain;
    }
    
    public void setGain(double val){
        this.gain = val;
    }
    
    public double getPercent(){
        return this.percentGain;
    }
    
    public String getDate(){
        return this.tradeDate;
    }
    
    public String getTime(){
        return this.tradeTime;
    }
}