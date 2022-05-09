/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import PaperAccount.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Owner
 */
public class AccountPanel extends javax.swing.JPanel {

    private DefaultListModel<String> startDLM;
    private DefaultListModel<String> endDLM;
    private DefaultListModel<String> gainDLM;
    private DefaultListModel<String> percentDLM;
    private DefaultListModel<String> dateDLM;
    private DefaultListModel<String> speedDLM;
    
    private Timer timer; 
    
    // Account Metric Variables
    private double balance; 
    private int numTrades;
    private int numWins; 
    private double winRate;
    private double avgTrade;
    private double totalGain;
    private double percentGain;
    private double lot;
    
    // Variables for reading data from the server
    private Gson gson;
    
    /**
     * Creates new form Account
     */
    public AccountPanel() {
        
        initComponents();
        
        this.startDLM = new DefaultListModel<String>();
        this.endDLM = new DefaultListModel<String>();
        this.gainDLM = new DefaultListModel<String>();
        this.percentDLM = new DefaultListModel<String>();
        this.dateDLM = new DefaultListModel<String>();
        this.speedDLM = new DefaultListModel<String>();
        
        this.balance = 5; 
        this.numTrades = 0;
        this.numWins = 0;
        this.winRate = 0;
        this.avgTrade = 0; 
        this.totalGain = 0;
        this.percentGain = 0; 
        this.lot = 1; 
        
        this.gson = new Gson();
        
        findNewTrades();
        
        updateAccountUI();
        
        int delay = 1000; 
        ActionListener taskPerformer = (ActionEvent evt) -> {
            findNewTrades();
            
        };
        
        this.timer = new javax.swing.Timer(delay, taskPerformer);
        this.timer.setInitialDelay(0);
        this.timer.start();
    }
    
    private void findNewTrades(){

        try{
            InputStream is = new URL("http://127.0.0.1:9000/tradestosend").openStream();
            InputStreamReader isr = new InputStreamReader(is); 
            
            GetNumTrades trades = this.gson.fromJson(isr, GetNumTrades.class);
            
            int numTrades = trades.getNumTrades();
            System.out.println("There are " + numTrades + " new trades"); 
            
            for (int i = 0; i < numTrades; i++) {
                getTrades(); 
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(AccountPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccountPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getTrades(){
        
        try{
            InputStream is = new URL("http://127.0.0.1:9000/trades").openStream();
            InputStreamReader isr = new InputStreamReader(is); 
            Trade.TradeData data = this.gson.fromJson(isr, Trade.TradeData.class);
            Trade trade = new Trade(data);
            newTrade(trade); 
        } catch (MalformedURLException ex) {
            Logger.getLogger(AccountPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccountPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static class GetNumTrades{
        private int numTrades;
        public int getNumTrades(){
            return numTrades; 
        }
    } 
    
    public void updateAccountUI(){
        
        String temp = String.format("%.5f", this.balance);
        this.value.setText(temp + " BTC");
            
        String totalReturn = String.format("%.5f", this.totalGain);
        if(this.totalGain > 0)
            this.returnLbl.setText("+" + totalReturn);
        else
            this.returnLbl.setText(totalReturn);
            
        String percentReturn = String.format("%.2f", this.percentGain);
        this.percentLbl.setText(percentReturn + "%");
            
        String win = String.format("%.3f", this.winRate);
        this.winRateLbl.setText(win + "%");

        String avg = String.format("%.5f", this.avgTrade);
        this.avgTradeLbl.setText(avg + " BTC");
            
        String lot = String.valueOf(this.lot);
        lotLbl.setText(lot);
        
        this.dateJList.setModel(this.dateDLM); 
        this.startJList.setModel(this.startDLM);
        this.endJList.setModel(this.endDLM);
        this.gainJList.setModel(this.gainDLM);
        this.percentJList.setModel(this.percentDLM);
        this.speedJList.setModel(this.speedDLM);
            
        repaint();
    }
    
    private void calculateAccountMetrics(Trade newTrade){
        
        this.numTrades++;
        this.balance += newTrade.getGain();
        this.totalGain += newTrade.getGain(); 
        
        if(newTrade.getGain() > 0)
            this.numWins++; 
        
        this.winRate = (double)this.numWins / this.numTrades * 100;
        
        this.avgTrade = this.totalGain / this.numTrades; 
        this.percentGain = (this.totalGain / 5) * 100; 
        
    }
    
    private void resetAccountMetrics(){
        this.balance = 5; 
        this.numTrades = 0;
        this.numWins = 0;
        this.winRate = 0;
        this.avgTrade = 0; 
        this.totalGain = 0;
        this.percentGain = 0; 
    }
    
    public void newTrade(Trade newTrade){
        String start = String.format("%.5f", newTrade.getStart());
        this.startDLM.insertElementAt(start, 0);
        
        String end = String.format("%.5f", newTrade.getFinal());
        this.endDLM.insertElementAt(end, 0);
        
        String gain = String.format("%.5f", newTrade.getGain());
        this.gainDLM.insertElementAt(gain, 0); 
        
        String percent = String.format("%.5f", newTrade.getPercent());
        this.percentDLM.insertElementAt(percent, 0); 
        
        this.dateDLM.insertElementAt(newTrade.getDate(), 0); 
        
        this.speedDLM.insertElementAt(newTrade.getTime(), 0); 
        
        calculateAccountMetrics(newTrade); 
        
        updateAccountUI();
    }
    
    public void resetAccount(){
        this.dateDLM.clear();
        this.startDLM.clear();
        this.endDLM.clear();
        this.gainDLM.clear();
        this.percentDLM.clear();
        this.speedDLM.clear();
        
        resetAccountMetrics();
        
        updateAccountUI();
    }
    
    private void dummyTrade(){
            
        // This was only for showing functionality in a live demonstration
        // Serves no purpose with the ability to use the time-lapse video
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        value = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        endJList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        startJList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        gainJList = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        percentJList = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        speedJList = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        winRateLbl = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        avgTradeLbl = new javax.swing.JLabel();
        resetBtn = new javax.swing.JButton();
        dummyBtn = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        dateJList = new javax.swing.JList<>();
        changeLotBtn = new javax.swing.JButton();
        lotLbl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        percentLbl = new javax.swing.JLabel();
        returnLbl = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1043, 547));
        setMinimumSize(new java.awt.Dimension(1043, 547));
        setPreferredSize(new java.awt.Dimension(1043, 547));

        value.setFont(new java.awt.Font("Sitka Banner", 1, 24)); // NOI18N
        value.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        value.setText("jLabel1");

        endJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(endJList);

        jLabel1.setFont(new java.awt.Font("Sitka Heading", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Trade Date");

        startJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(startJList);

        jLabel2.setFont(new java.awt.Font("Sitka Heading", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Start Amount");

        jLabel3.setFont(new java.awt.Font("Sitka Heading", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("End Amount");

        gainJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(gainJList);

        jLabel4.setFont(new java.awt.Font("Sitka Heading", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Gain/Loss");

        percentJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(percentJList);

        jLabel5.setFont(new java.awt.Font("Sitka Heading", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Percent Gain/Loss");

        speedJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(speedJList);

        jLabel6.setFont(new java.awt.Font("Sitka Heading", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Trade Time");

        jLabel7.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Percent Gain:");

        winRateLbl.setFont(new java.awt.Font("Sitka Display", 0, 18)); // NOI18N
        winRateLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        winRateLbl.setText("jLabel8");

        jLabel8.setFont(new java.awt.Font("Sitka Subheading", 0, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Avg. Trade");

        avgTradeLbl.setFont(new java.awt.Font("Sitka Display", 0, 18)); // NOI18N
        avgTradeLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avgTradeLbl.setText("jLabel8");

        resetBtn.setText("Reset Account");
        resetBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetBtnMouseClicked(evt);
            }
        });
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        dummyBtn.setText("Dummy Trade");
        dummyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dummyBtnMouseClicked(evt);
            }
        });
        dummyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dummyBtnActionPerformed(evt);
            }
        });

        dateJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(dateJList);

        changeLotBtn.setText("Change Lot");
        changeLotBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeLotBtnMouseClicked(evt);
            }
        });
        changeLotBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeLotBtnActionPerformed(evt);
            }
        });

        lotLbl.setFont(new java.awt.Font("Sitka Subheading", 0, 24)); // NOI18N
        lotLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lotLbl.setText("0");

        jLabel10.setFont(new java.awt.Font("Sitka Subheading", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Lot: ");

        jLabel9.setFont(new java.awt.Font("Sitka Subheading", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Win Rate");

        jLabel11.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Total Return:");

        percentLbl.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        percentLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        percentLbl.setText("%");

        returnLbl.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        returnLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        returnLbl.setText("$");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lotLbl))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(returnLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(value, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(percentLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resetBtn)
                        .addGap(18, 18, 18)
                        .addComponent(dummyBtn)
                        .addGap(18, 18, 18)
                        .addComponent(changeLotBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(winRateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avgTradeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(returnLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(value, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(percentLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lotLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(winRateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(avgTradeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane6)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                            .addComponent(jScrollPane4)
                            .addComponent(jScrollPane7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resetBtn)
                            .addComponent(dummyBtn)
                            .addComponent(changeLotBtn))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void resetBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetBtnMouseClicked
        
    }//GEN-LAST:event_resetBtnMouseClicked

    private void dummyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dummyBtnMouseClicked
        
    }//GEN-LAST:event_dummyBtnMouseClicked

    private void dummyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dummyBtnActionPerformed
        dummyTrade();
    }//GEN-LAST:event_dummyBtnActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        resetAccount();
    }//GEN-LAST:event_resetBtnActionPerformed

    private void changeLotBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeLotBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_changeLotBtnMouseClicked

    private void changeLotBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeLotBtnActionPerformed
        // Prompt user for new lot allocation
        String lot = JOptionPane.showInputDialog(this, "Enter Desired Lot Amount", "Set Lot", 3);
        float setLot = Float.parseFloat(lot);
        
        this.lot = setLot;
    }//GEN-LAST:event_changeLotBtnActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avgTradeLbl;
    private javax.swing.JButton changeLotBtn;
    private javax.swing.JList<String> dateJList;
    private javax.swing.JButton dummyBtn;
    private javax.swing.JList<String> endJList;
    private javax.swing.JList<String> gainJList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lotLbl;
    private javax.swing.JList<String> percentJList;
    private javax.swing.JLabel percentLbl;
    private javax.swing.JButton resetBtn;
    private javax.swing.JLabel returnLbl;
    private javax.swing.JList<String> speedJList;
    private javax.swing.JList<String> startJList;
    private javax.swing.JLabel value;
    private javax.swing.JLabel winRateLbl;
    // End of variables declaration//GEN-END:variables
}