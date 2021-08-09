package com.sdet.automationFramework.utilities;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class PortForwardingL {

    public PortForwardingL() {
    }

    public Session openSessionToForwardPortToZeusDev(
            int lport, String rhost, int rport , String userName, String password, String host
    ) throws JSchException {

        JSch jsch=new JSch();
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        Session session =jsch.getSession(userName, host, 22);
        session.setPassword(password);
        session.setConfig(config);
        session.connect();
        int assinged_port = session.setPortForwardingL(lport, rhost, rport);
        //System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);

        return session;
    }

    public void closeSession(Session session){
        session.disconnect();
//        System.out.println("Session Closed");
    }

}
