package com.messaging.senders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Client {
    //address and port where to connect
    private String address;
    private int port;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void doWork() {
        try {
            // getting localhost ip
            InetAddress ip = InetAddress.getByName(address);

            // establish the connection with server port
            Socket socket = new Socket(ip, port);

            //Scanner scn = new Scanner(System.in);
            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            //server text
            String serverText = dis.readUTF();
            System.out.println(serverText);

            DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

            // the following loop performs the exchange of
            // information between client and client handler
            int sent = 0;
            int received = 0;
            while (true) {
                try {
                    //client text
                    String clientText = LocalDateTime.now().format(FOMATTER);
                    dos.writeUTF(clientText);
                    sent++;

                    //what server sends
                    serverText = dis.readUTF();
                    System.out.println(serverText);
                    received++;

                    //pause for random seconds to imitate async talking
                    try {
                        int seconds = 2000 + (int) (System.currentTimeMillis() % 5) * 1000;
                        System.out.println("[port=" + socket.getLocalPort() + "] paused for " + seconds / 1000 + "sec");
                        Thread.sleep(seconds);
                    } catch (InterruptedException t) {
                    }

                    // If client sends more then 10 messages
                    // and then break from the while loop
                    if (sent >= 10 && (sent == received)) {
                        System.out.println("1.[Client] Closing this connection : " + socket);
                        break;
                    }
                } catch (Exception t) {
                    System.out.println("2.[Client] Closing this connection : " + socket);
                    t.printStackTrace();
                    break;
                }
            }

            // closing resources
            dis.close();
            dos.close();
            socket.close();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}