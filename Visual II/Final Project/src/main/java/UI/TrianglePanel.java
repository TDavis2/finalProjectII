/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Data.Prices;
import Data.Conversions;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Owner
 */
 public class TrianglePanel extends javax.swing.JPanel {

    private Prices prices; 
    private Conversions conversions;
    
    /**
    * Creates new form Triangle
    */

    public TrianglePanel() {
        int scalar = 58; 
        this.prices = new Prices();
        this.conversions = new Conversions(); 
        
        initComponents();
        
        Image btcImage = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\resources\\btcImg.png");
        btcImage = btcImage.getScaledInstance(scalar, scalar, scalar); 
        Icon btcIcon = new ImageIcon(btcImage);
        
        Image ltcImage = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\resources\\ltcImg.png");
        ltcImage = ltcImage.getScaledInstance(scalar, scalar, scalar); 
        Icon ltcIcon = new ImageIcon(ltcImage); 
        
        Image ethImage = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\resources\\ethImg.png");
        ethImage = ethImage.getScaledInstance(scalar, scalar, scalar); 
        Icon ethIcon = new ImageIcon(ethImage); 
        
//        Image leftArrowHead = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\resources\\arrowIcon.jpg");
//        leftArrowHead = leftArrowHead.getScaledInstance(scalar, scalar, scalar); 
//        Icon leftArrowHeadIcon = new ImageIcon(leftArrowHead);
//        
//        Image leftArrowTail = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\resources\\arrowIcon.jpg");
//        leftArrowTail = leftArrowTail.getScaledInstance(scalar, scalar, scalar); 
//        Icon leftArrowTailIcon = new ImageIcon(leftArrowTail);

        btcLbl.setIcon(btcIcon);
        ltcLbl.setIcon(ltcIcon);
        ethLbl.setIcon(ethIcon); 
        
//        leftArrowTailLbl.setIcon(leftArrowTailIcon);
//        leftArrowHeadLbl.setIcon(leftArrowHeadIcon);
        
        if(1 == 1)
            startStopBtn.setText("Stop");
        else
            startStopBtn.setText("Start");
    }
    
    public void updateTriangleUI(){
        if(this.prices != null)
        {
            ///////////////////////////     UPDATE CONVERSION RATES     ///////////////////////////
            this.prices.updatePrices();

            String rate1 = String.format("%.5f", this.prices.getBTCLTCRate());
            this.btcltcRateLbl.setText(rate1);

            String rate2 = String.format("%.5f", this.prices.getLTCETHRate());
            this.ltcethRateLbl.setText(rate2);
            
            String rate3 = String.format("%.5f", this.prices.getETHBTCRate());
            this.ethbtcRateLbl.setText(rate3); 
            
            ///////////////////////////     UPDATE COIN AMOUNTS     ///////////////////////////
            this.conversions.updateAmts();

            String btcStart = String.format("%.5f", this.conversions.getbtcStart());
            this.btcAmtStart.setText(btcStart);

            String ltcAmt = String.format("%.5f", this.conversions.getltcAmt()); 
            this.ltcAmt.setText(ltcAmt);

            String ethAmt = String.format("%.5f", this.conversions.getethAmt());
            this.ethAmt.setText(ethAmt);
            
            String btcFinal = String.format("%.5f", this.conversions.getbtcFinal());
            this.btcAmtFinal.setText(btcFinal); 
        }
        if(1 == 1)
            startStopBtn.setText("Stop");
        else
            startStopBtn.setText("Start");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        homeBackground = new javax.swing.JPanel();
        titleLbl = new javax.swing.JLabel();
        ltcLbl = new javax.swing.JLabel();
        btcLbl = new javax.swing.JLabel();
        ethLbl = new javax.swing.JLabel();
        btcltcRateLbl = new javax.swing.JLabel();
        ltcethRateLbl = new javax.swing.JLabel();
        ethbtcRateLbl = new javax.swing.JLabel();
        btcAmtStart = new javax.swing.JLabel();
        ethAmt = new javax.swing.JLabel();
        ltcAmt = new javax.swing.JLabel();
        btcAmtFinal = new javax.swing.JLabel();
        startStopBtn = new javax.swing.JButton();

        titleLbl.setFont(new java.awt.Font("Sitka Heading", 0, 18)); // NOI18N
        titleLbl.setText("Bitcoin Triangular Arbitrage Bot");

        ltcLbl.setFont(new java.awt.Font("Sitka Display", 1, 14)); // NOI18N
        ltcLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btcLbl.setFont(new java.awt.Font("Sitka Display", 1, 14)); // NOI18N
        btcLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        ethLbl.setFont(new java.awt.Font("Sitka Display", 1, 14)); // NOI18N
        ethLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btcltcRateLbl.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        btcltcRateLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btcltcRateLbl.setText("");

        ltcethRateLbl.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        ltcethRateLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ltcethRateLbl.setText("");

        ethbtcRateLbl.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        ethbtcRateLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ethbtcRateLbl.setText("");

        btcAmtStart.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        btcAmtStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btcAmtStart.setText("");

        ethAmt.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        ethAmt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ethAmt.setText("");

        ltcAmt.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        ltcAmt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ltcAmt.setText("");

        btcAmtFinal.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        btcAmtFinal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btcAmtFinal.setText("");

        startStopBtn.setText("Start");
        startStopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStopBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout homeBackgroundLayout = new javax.swing.GroupLayout(homeBackground);
        homeBackground.setLayout(homeBackgroundLayout);
        homeBackgroundLayout.setHorizontalGroup(
            homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeBackgroundLayout.createSequentialGroup()
                .addGroup(homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homeBackgroundLayout.createSequentialGroup()
                        .addGap(363, 363, 363)
                        .addGroup(homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(titleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(homeBackgroundLayout.createSequentialGroup()
                                .addComponent(btcAmtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(startStopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(homeBackgroundLayout.createSequentialGroup()
                                        .addComponent(btcLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btcAmtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(homeBackgroundLayout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(ethAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ethLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(149, 149, 149)
                        .addComponent(ltcethRateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                        .addComponent(ltcLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ltcAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(177, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homeBackgroundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ethbtcRateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163)
                .addComponent(btcltcRateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(377, 377, 377))
        );
        homeBackgroundLayout.setVerticalGroup(
            homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btcAmtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btcAmtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btcLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btcltcRateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ethbtcRateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homeBackgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ltcAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ethAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(147, 147, 147))
                    .addGroup(homeBackgroundLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(homeBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ltcLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ethLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ltcethRateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(startStopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(homeBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(homeBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startStopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startStopBtnActionPerformed
        
        updateTriangleUI();
    }//GEN-LAST:event_startStopBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btcAmtFinal;
    private javax.swing.JLabel btcAmtStart;
    private javax.swing.JLabel btcLbl;
    private javax.swing.JLabel btcltcRateLbl;
    private javax.swing.JLabel ethAmt;
    private javax.swing.JLabel ethLbl;
    private javax.swing.JLabel ethbtcRateLbl;
    private javax.swing.JPanel homeBackground;
    private javax.swing.JLabel ltcAmt;
    private javax.swing.JLabel ltcLbl;
    private javax.swing.JLabel ltcethRateLbl;
    private javax.swing.JButton startStopBtn;
    private javax.swing.JLabel titleLbl;
    // End of variables declaration//GEN-END:variables
}
