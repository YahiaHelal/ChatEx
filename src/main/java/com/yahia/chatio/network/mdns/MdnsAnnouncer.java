package com.yahia.chatio.network.mdns;

import com.yahia.chatio.utils.logging.LogManager;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MdnsAnnouncer {
    private JmDNS jmDNS;
    private ServiceInfo serviceInfo;
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String CHAT_SERVICE_TYPE = "_chat_._tcp.local.";

    public void announce(String serverName, int port) throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        jmDNS = JmDNS.create(addr);

        //NOTE: may add No. users to the metadata
        serviceInfo = ServiceInfo.create(CHAT_SERVICE_TYPE, serverName, port, "");
        jmDNS.registerService(serviceInfo);
        LOGGER.log(Level.INFO, String.format("New Chat Server Announced: %s", serviceInfo.getName()));
    }


    public void stopService(ServiceInfo info) throws Exception{
        if(jmDNS != null && info != null) {
            jmDNS.unregisterService(info);
            LOGGER.log(Level.INFO, String.format("Unregister Chat Server:  %s", serviceInfo.getName()));
        }
    }

    //NOTE: any MdnsAnnouncer instance can stop all services of a certain service type
    public void stopAll() throws Exception{
        if(jmDNS != null) {
            jmDNS.unregisterAllServices();
            jmDNS.close();
        }
    }
}
