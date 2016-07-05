package chat.pk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paweł Kuźmicki
 */
public class Client {
    private ClientGUI gui;
    
    private String name;
    
    private DatagramSocket socket;
    private InetAddress serverIpAddress;
    private final int serverPort = 54381;
    
    private Thread receiveThread;
    
    public Client(){
        
    }
    
    public Client(String name, InetAddress serverIp){
        this.serverIpAddress = serverIp;
        this.name = name.toUpperCase();
        
        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            System.err.println("Client nie moze zając socketu");            
            ex.printStackTrace();
        }
        receiveFromServer();
        
       // initializeConnection();
    }
    
    public void initializeConnection() {
        String firstMessage = "/f/" + this.name; //My protocol - /f/ means first connection to server
        sendToServer(firstMessage);
    }
    
    public void sendToServer(String message){
        byte[] buf = new byte[1024];
        
        buf = message.getBytes();
        
        DatagramPacket packet = new DatagramPacket(buf, buf.length, serverIpAddress, serverPort);
        
        try {
            socket.send(packet);
        } catch (IOException ex) {
            System.err.println("Błąd przy wysyłaniu od clienta do servera");
            ex.printStackTrace();
        }
    }
    
    public void receiveFromServer(){
        receiveThread = new Thread("receiveThreadClient"){
            public void run(){
                while(true){
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    
                    try {
                        socket.receive(packet);
                    } catch (IOException ex) {
                        System.err.println("Błąd prz odbieraniu packetu przez clienta");
                        ex.printStackTrace();
                    }
                    doSthWithReceived(packet, buf);
                }
            }

        };
        receiveThread.start();
    }
    

    private void doSthWithReceived(DatagramPacket packet, byte[] buf) {
        String receivedMessage = new String(buf).trim();
        gui.showInMainTextArea(receivedMessage);
    }    

    public String getName() {
        return name;
    }

    public void setGui(ClientGUI gui) {
        this.gui = gui;
    }

}
