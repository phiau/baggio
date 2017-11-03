package com.yangqugame;

import jazmin.core.Jazmin;
import jazmin.server.protobuf.ProtobufServer;

/**
 * Created by phiau on 2017/10/30 0030.
 */
public class StartMessage {

    public static void main(String[] args) {
        Thread shutdownThread=new Thread(Jazmin::stop);
        shutdownThread.setName("ShutdownHook");
        Runtime.getRuntime().addShutdownHook(shutdownThread);
        Jazmin.dumpLogo();
        Jazmin.addServer(new ProtobufServer());
        Jazmin.loadApplication(new BaggioMessageApplication());
        Jazmin.start();
    }
}
