package com.yahia.chatio.network.mdns;

import com.yahia.chatio.utils.logging.LogManager;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MdnsDiscovery {
    private static final String SERVICE_TYPE = "_chat_._tcp.local";
    private static final Logger LOGGER = LogManager.getLogger();
    public void discover() throws Exception {

        InetAddress addr = InetAddress.getLocalHost();
        JmDNS jmDNS = JmDNS.create(addr);

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
                LOGGER.log(Level.INFO, String.format("Server %s Left", serviceEvent.getName()));
            }

            @Override
            public void serviceResolved(ServiceEvent serviceEvent) {
                ServiceInfo info = serviceEvent.getInfo();

                String serverName = info.getName();
                String ip = info.getInet4Addresses()[0].getHostAddress();
                int port = info.getPort();

                LOGGER.log(Level.INFO, String.format("Server found: Name: %s, IP: %s, Port: %d", serverName, ip, port));

                //update ui ?
            }
        });
    }
}
