package com.yahia.chatio.network.mdns;

import com.yahia.chatio.utils.logging.LogManager;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MdnsDiscovery {
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
            }

            @Override
            public void serviceRemoved(ServiceEvent serviceEvent) {
                String name = serviceEvent.getName();
                discoveredServers.remove(name);
                LOGGER.log(Level.INFO, String.format("Server %s Left", name));
            }

            @Override
            public void serviceResolved(ServiceEvent serviceEvent) {
                ServiceInfo info = serviceEvent.getInfo();

                String serverName = info.getName();
                InetAddress[] ipv4 = info.getInet4Addresses();
                int port = info.getPort();
                if(ipv4.length == 0) {
                    LOGGER.log(Level.WARNING, String.format("No IPv4 address for %s", serverName));
                    return;
                }
                String ip = ipv4[0].getHostAddress();
                discoveredServers.put(serverName, new InetSocketAddress(ip, port));
                LOGGER.log(Level.INFO, String.format("Server found: Name: %s, IP: %s, Port: %d", serverName, ip, port));

                //update ui ?
            }
        });
    }

    public InetSocketAddress getServerAddress(String serverName) {
        return discoveredServers.get(serverName);
    }
}
