package com.yahia.chatio.network.mdns;

import com.yahia.chatio.utils.logging.LogManager;
import javafx.application.Platform;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MdnsDiscovery implements AutoCloseable {
    private static final String SERVICE_TYPE = "_chat_._tcp.local.";
    private static final Logger LOGGER = LogManager.getLogger();
    private JmDNS jmDNS;
    private final Map<String, InetSocketAddress> discoveredServers = new ConcurrentHashMap<>();

    public void start() throws Exception {
        if(jmDNS != null) return;
        InetAddress addr = InetAddress.getLocalHost();
        jmDNS = JmDNS.create(addr);

        jmDNS.addServiceListener(SERVICE_TYPE, new ServiceListener() {
            @Override
            public void serviceAdded(ServiceEvent serviceEvent) {
                jmDNS.requestServiceInfo(
                        serviceEvent.getType(),
                        serviceEvent.getName(),
                        true
                );
                Platform.runLater(() -> {
                    // update ui
                });
            }

            @Override
            public void serviceRemoved(ServiceEvent event) {
                discoveredServers.remove(event.getName());
                LOGGER.log(Level.INFO, String.format("Server %s Left", event.getName()));

                Platform.runLater(() -> {
                    // update ui
                });
            }

            @Override
            public void serviceResolved(ServiceEvent event) {
                ServiceInfo info = event.getInfo();

                InetAddress[] ipv4 = info.getInet4Addresses();
                if(info.getInet4Addresses().length == 0) {
                    LOGGER.log(Level.WARNING, String.format("No IPv4 address for %s", info.getName()));
                    return;
                }

                String ip = ipv4[0].getHostAddress();
                int port = info.getPort();

                discoveredServers.put(event.getName(), new InetSocketAddress(ip, port));
                LOGGER.log(Level.INFO, String.format("Server found: Name: %s, IP: %s, Port: %d", event.getName(), info.getInet4Addresses()[0], info.getPort()));
            }
        });
    }

    public synchronized void close() {
        if(jmDNS != null) {
            try {
                jmDNS.close();
            }catch (IOException e) {
                LOGGER.log(Level.WARNING, "Failed to close mDNS discovery", e);
            }finally {
                jmDNS = null;
            }
        }
    }


    public InetSocketAddress getServerAddress(String serverName) {
        return discoveredServers.get(serverName);
    }

    public void removeServer(String serverName) {
        discoveredServers.remove(serverName);
    }
}
