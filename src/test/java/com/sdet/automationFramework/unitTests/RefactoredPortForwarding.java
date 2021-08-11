package com.sdet.automationFramework.unitTests;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class RefactoredPortForwarding {

    public static void main(String[] arg) throws JSchException, InterruptedException {

        int lport = 9090;
        String rhost = "";
        int rport = 1908;

        JSch jsch=new JSch();
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        Session session=jsch.getSession("", "", 22);
        session.setPassword("");
        session.setConfig(config);
        session.connect();
        int assinged_port = session.setPortForwardingL(lport, rhost, rport);
        System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);

        Thread.sleep(10000);
        System.out.println("Slept for ten seconds");

        session.disconnect();
        System.out.println("Session Closed");

    }
}
