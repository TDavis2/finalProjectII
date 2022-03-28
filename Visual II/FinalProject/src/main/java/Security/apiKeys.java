/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

/**
 *
 * @author Owner
 */
public class apiKeys {
    
    private static String binanceAPI;
    private static String binanceSecret;
    
    
    public apiKeys(){
        
        //////////////////////////////////////////////////          KEYS FOR BINANCE            //////////////////////////////////////////////////
        this.binanceAPI = "TaFvm4InXL5RGMecPHN3J9u0ocqtD4e3p0iYcWdgFaH5eBDuDn2e4EYCP5kF3lc5";
        this.binanceSecret = "Jg4fzRum44joCV8BcvDO9IimecgPMxLtVQ06SudMp4KMRSsBFkB4T5rIA5uOFXD4";
        
        
        
        
    }
    
    public String getBinanceAPI(){
        return this.binanceAPI;
    }
    
    public String getBinanceSecret(){
        return this.binanceSecret; 
    }
    
}
