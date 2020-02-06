package com.messaging;

import java.io.IOException;

/**
 * @author NIsaev on 19.12.2019
 */
public class Main {


    public static void main(String[] args) throws IOException {
        try {
            Player server = new Player(2289);
            server.start();

            Player client1 = new Player("localhost", 2289);
            client1.start();

            Player client2 = new Player("localhost", 2289);
            client2.start();

            Player client3 = new Player("localhost", 2289);
            client3.start();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

}
