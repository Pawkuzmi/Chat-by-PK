/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.pk;

import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paweł Kuźmicki
 */
public class ClientGUI extends javax.swing.JFrame {

    Client client;
    
    public ClientGUI(Client c) {
        initComponents();
        this.client = c;
        c.setGui(this);
        c.initializeConnection();
    }

    public void setClient(Client client) {
        this.client = client;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mainTextArea = new javax.swing.JTextArea();
        southPanel = new javax.swing.JPanel();
        messageTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat PK");
        setLocation(new java.awt.Point(500, 250));
        setMinimumSize(new java.awt.Dimension(400, 300));

        mainTextArea.setColumns(20);
        mainTextArea.setRows(5);
        jScrollPane1.setViewportView(mainTextArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        southPanel.setLayout(new javax.swing.BoxLayout(southPanel, javax.swing.BoxLayout.LINE_AXIS));

        messageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTextFieldKeyPressed(evt);
            }
        });
        southPanel.add(messageTextField);

        sendButton.setText("Send!");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        southPanel.add(sendButton);

        getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        send();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void messageTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            send();
    }//GEN-LAST:event_messageTextFieldKeyPressed

   public void send(){
       String message ="/m/" + client.getName()+ " - " + messageTextField.getText();
        client.sendToServer(message);
        
        messageTextField.setText("");
    }
   
   public void showInMainTextArea(String message){
       mainTextArea.append(message + "\n\n");
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea mainTextArea;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JButton sendButton;
    private javax.swing.JPanel southPanel;
    // End of variables declaration//GEN-END:variables
}