package com.yahia.chatio.network.mdns;

import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.utils.logging.LogManager;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MdnsAnnouncer implements AutoCloseable {
    private static MdnsAnnouncer INSTANCE;

    private JmDNS jmDNS;
    private final Map<String, ServiceInfo> services = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CHAT_SERVICE_TYPE = "_chat_._tcp.local.";



    private MdnsAnnouncer() throws IOException {
        InetAddress addr = InetAddress.getLocalHost();
        jmDNS = JmDNS.create(addr); // one creation per machine [reserved port for mdns]
    }

    public static MdnsAnnouncer getInstance() throws IOException {
        if(INSTANCE == null) {
            INSTANCE = new MdnsAnnouncer();
        }
        return INSTANCE;
    }

    public void announce(ServerConnection connection) throws IOException {
        ServiceInfo info = ServiceInfo.create(CHAT_SERVICE_TYPE, connection.name(), connection.port(), "");
        jmDNS.registerService(info);
        services.put(connection.name(), info);
        LOGGER.log(Level.INFO, String.format("New Chat Server Announced: %s", connection.name()));
    }



    public void stopService(String serverName){
        ServiceInfo info = services.remove(serverName);
        if(jmDNS != null) {
            jmDNS.unregisterService(info);
            LOGGER.log(Level.INFO, String.format("Unregister Chat Server:  %s", serverName));
        }
    }

    public void shutdown() throws Exception{
        if(jmDNS != null) {
            jmDNS.unregisterAllServices();
            jmDNS.close();
        }
    }

    @Override
    public void close() throws Exception {
        if(jmDNS != null) {
            try {
                jmDNS.close();
            }catch (IOException e) {
                LOGGER.log(Level.WARNING, "Couldn't close mDNS Announcer");
            }finally {
                jmDNS = null;
            }
        }
    }
}
