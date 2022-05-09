/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import PaperAccount.TradeHistory; 
import PaperAccount.Trade;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;


/**
 *
 * @author Tyler Davis
 */
public class HTTPServer {
    
    public static void handleSetLot(HttpExchange t) throws IOException {
        
        try{
            HashMap<String, String> map = parseQueryMap(t);
            String strLot = map.get("lot");
            
            int lot = Integer.parseInt(strLot);  
            
            Headers h = t.getRequestHeaders();
            String response = "<HTML><body><H1>Hi " + lot + "</H1><br/><hr/></body></HTML>";
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e){
            String response = "<HTML><body><H1>" + e.toString() + "</H1><br/><hr/></body></HTML>";
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
    }
    
    /***
     * HashMap<String, String> qMap = ServerExample.getQueryMap(t);
     * 
     * @param t
     * @return 
     */
    public static HashMap<String, String> parseQueryMap(HttpExchange t){
        HashMap<String, String> map = new HashMap<String, String>();
        
        String tmp = t.getRequestURI().getQuery();
        if(tmp == null)
            return map;
        
        String[] pairs = tmp.split("&");
        for(String pair : pairs){
            String[] kvPair = pair.split("=");
            if(kvPair.length == 2)
                map.put(kvPair[0], kvPair[1]);
            else if(kvPair.length == 1)
                map.put(kvPair[0], "");
        }
        
        return map;
    }
    
    // Sends the new trades to the client
    public static void handleGetTrade(HttpExchange t) throws IOException{
        
        OutputStream os = t.getResponseBody();
        
        Trade trade = TradeHistory.getInstance().getTradeNum(TradeHistory.getInstance().getTradesSent());
        TradeHistory.getInstance().incrementTradesSent();
        
        Headers h = t.getRequestHeaders();
        String response = trade.toJSON();
        t.sendResponseHeaders(200, response.getBytes().length);
        os.write(response.getBytes());
        os.close();
        
    }
    
    // Sends the number of new trades in the server to the client
    public static void handleGetNumTrades(HttpExchange t) throws IOException{
        OutputStream os = t.getResponseBody();
        int tradesToSend = TradeHistory.getInstance().getTrades() - TradeHistory.getInstance().getTradesSent();
        
        Headers h = t.getRequestHeaders();
        
        StringBuilder json = new StringBuilder();
        
        json.append('{');
        
        json.append("\"numTrades\":\"");
        json.append(tradesToSend);
        json.append("\"");
            
        json.append('}');
        
        String response = json.toString();
        
        t.sendResponseHeaders(200, response.getBytes().length);
        os.write(response.getBytes());
       
        os.close();
    }
    
    // Sends the number of total trades in trade history to the client
    public static void handleGetTotalTrades(HttpExchange t) throws IOException{
        OutputStream os = t.getResponseBody();
        int tradesToSend = TradeHistory.getInstance().getTrades();
        
        Headers h = t.getRequestHeaders();
        
        StringBuilder json = new StringBuilder();
        
        json.append('{');
        
        json.append("\"numTrades\":\"");
        json.append(tradesToSend);
        json.append("\"");
            
        json.append('}');
        
        String response = json.toString();
        
        t.sendResponseHeaders(200, response.getBytes().length);
        os.write(response.getBytes());
       
        os.close();
    }
    
    // Sends all the trades in trade history to the client
    public static void handleSendAllTrades(HttpExchange t) throws IOException{
        OutputStream os = t.getResponseBody();
        
        Trade trade = TradeHistory.getInstance().getTradeNum(TradeHistory.getInstance().getSendAllCounter());
        TradeHistory.getInstance().incrementSendAllCounter();
        TradeHistory.getInstance().incrementTradesSent();
        
        if(TradeHistory.getInstance().getSendAllCounter() == TradeHistory.getInstance().getTrades()){
            TradeHistory.getInstance().resetSendAllCounter();
        }
        
        Headers h = t.getRequestHeaders();
        String response = trade.toJSON();
        t.sendResponseHeaders(200, response.getBytes().length);
        os.write(response.getBytes());
        os.close();
    }
    
    public static void main(String[] args) throws IOException{
        //ServerExample example = new ServerExample();
        
        Thread thread = new Thread(new TradingAlgo());
        thread.start();
        HttpServer server = HttpServer.create(new InetSocketAddress(9000),0);
        server.createContext("/trades", ( x ) -> { handleGetTrade(x); } );
        server.createContext("/tradestosend", ( x ) -> { handleGetNumTrades(x); } );
        server.createContext("/getalltrades", ( x ) -> { handleGetTotalTrades(x); } );
        server.createContext("/sendalltrades", ( x ) -> { handleSendAllTrades(x); } );
        server.createContext("/setlot", ( x ) -> { handleSetLot(x); } );
        server.setExecutor(null); // creates a default executor
        server.start(); 
    }     
    
}
