package chat.pk;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.UUID;

/**
 *
 * @author Paweł Kuźmicki
 */
public class ClientOnServer {
    private InetAddress ipAddress;
    private int port;
    
    private String name;
    private UUID id;

    public ClientOnServer(String name, InetAddress ipAddress, int port) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.port = port;
        id = UUID.randomUUID();
    }
    
    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }
    
    
}
