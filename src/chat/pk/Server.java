package chat.pk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paweł Kuźmicki
 */
public class Server {
    private DatagramSocket socket;
    private final int port = 54381;
    private Thread receiveThread;
    
    private ArrayList<ClientOnServer> clients;
    
    public Server(){
        clients = new ArrayList<>();
        
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            System.err.println("Server nie moze zając socketu");            
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        Server server = new Server();
        server.initialize();
    }

    private void initialize() {
        this.startReceiving();
        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startReceiving() {
        receiveThread = new Thread("receiveThreadServer"){
            public void run(){
                while(true){
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    System.out.println("działa");
                    try {
                        socket.receive(packet);
                    } catch (IOException ex) {
                        System.err.println("Błąd przy odbieraniu packetu w serverze");                        
                        ex.printStackTrace();
                    }
                    
                    doSthWithReceived(packet, buf);
                }
            }

        };
        receiveThread.start();
    }
    

    private void doSthWithReceived(DatagramPacket packet, byte[] buf) {
        String message = new String(buf);
        System.out.println(message);
        
        if(message.startsWith("/f/")){  //My protocol - /f/ means first connection to server
            String name = message.substring(3).trim();
            clients.add(new ClientOnServer(name, packet.getAddress(), packet.getPort()));
            String newMessageToSend = name + " joined conversation.";
            sendToEveryClient(newMessageToSend.toUpperCase());
        }
        else if(message.startsWith("/m/")){  //My protocol - /m/ means normal Message
            sendToEveryClient(message.substring(3));
        }
        else if(message.startsWith("/e/")){  //My protocol - /e/ means closed chat window
            String name = message.substring(3).trim();
            
            removeClientFromServer(name);
            clients.trimToSize();
            
            String newMessageToSend = name + " left conversation.";
            sendToEveryClient(newMessageToSend.toUpperCase());
            
        }
        
        System.out.println("liczba osób " + clients.size());
    }    

 /*   private void sendToEveryClient(byte[] buf) {
        for(ClientOnServer client : clients){
            DatagramPacket packet = new DatagramPacket(buf, buf.length, client.getIpAddress(), client.getPort());
            
            try {
                socket.send(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }*/
    
    private void sendToEveryClient(String message) {
        byte[] buf = new byte[1024];
        buf = message.getBytes();
        
        for(ClientOnServer client : clients){
            DatagramPacket packet = new DatagramPacket(buf, buf.length, client.getIpAddress(), client.getPort());
            
            try {
                socket.send(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void removeClientFromServer(String name) {
        if(clients.size() > 1){
            for(ClientOnServer client : clients){
                if(client.getName().equals(name))
                    clients.remove(client);
            }
        }
        else clients.clear();
    }
}
