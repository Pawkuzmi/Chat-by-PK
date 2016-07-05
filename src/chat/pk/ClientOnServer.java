package chat.pk;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 *
 * @author Paweł Kuźmicki
 */
public class ClientOnServer {
    private InetAddress ipAddress;
    private int port;
    
    private String name;

    public ClientOnServer(String name, InetAddress ipAddress, int port) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }
    
    
}
