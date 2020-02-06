package com.messaging;

import com.messaging.senders.Client;
import com.messaging.senders.Server;

/**
 * @author NIsaev on 19.12.2019
 */
public class Player extends Thread {
    //property Server
    private Server server;

    //property Client
    private Client client;

    private final boolean isServer;

    public Player(int port){
        //only server
        server = new Server(port);
        isServer = true;
    }

    public Player(String address, int port){
        //only client
        client = new Client(address, port);
        isServer = false;
    }

    @Override
    public void run(){
        if(isServer)
        {
            //work just server
            server.doWork();
        }
        else {
            //work just client
            client.doWork();
        }
    }
}
