/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchanges;

import Security.apiKeys; 

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerStatistics;

/**
 *
 * @author Owner
 */
public class binance {
    
    private static apiKeys keys; 
    
    private static BinanceApiRestClient client; 
    
    public binance(){
        
        this.keys = new apiKeys(); 
        
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(this.keys.getBinanceAPI(), this.keys.getBinanceSecret());
        this.client = factory.newRestClient();
        
    }
    
    public double getPrice(String symbol){
        double price;
        
        TickerStatistics tickerStatistics = this.client.get24HrPriceStatistics(symbol);
        price = Double.parseDouble(tickerStatistics.getLastPrice());
        
        return price; 
    }
    
    
}

