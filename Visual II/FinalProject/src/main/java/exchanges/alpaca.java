/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchanges;

import UI.Home;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.account.Account;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.historical.quote.LatestQuoteResponse;
import net.jacobpeterson.alpaca.rest.AlpacaClientException;


/**
 *
 * @author Owner
 */
public class alpaca {
    
    public static Account account;
    public static AlpacaAPI alpacaAPI;
    
    public alpaca(){
        
        /////////////////////////////////////////////////////////////           CREATES ACCOUNT ENDPOINT            /////////////////////////////////////////////////////////////  
        
        this.alpacaAPI = new AlpacaAPI();
        
        try {
            // Get 'Account' information and print it out
            this.account = this.alpacaAPI.account().get();
            System.out.println(this.account);
        } catch (AlpacaClientException exception) {
            exception.printStackTrace();
        }
        
    }
    
    public double getBTCPrice(){
        double price = 0;
        
        return price; 
    }
    
}
