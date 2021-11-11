package com.charlie.imserver.service;

import com.charlie.imcommon.Message;
import com.charlie.imcommon.MessageType;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Set;

/**
 * hold a socket object and keep correspondence with client
 *
 * @author AC
 * @version 1.0
 * @date 10/14/2021
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;  //the user who connect with server

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    /**
     * override the run() method
     * establish a connection between server and client with socket object
     * transport message object by object input/output streams
     * package socket object in thread for independence
     */
    @Override
    public void run() { //receive or send message to client

        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        //send the offline message to client then delete these offline message from offline DB
        if (!IMServer.getOfflineDB().get(userId).isEmpty()) {
            for (Message msg : IMServer.getOfflineDB().get(userId)) {
                try {
                    oos = new ObjectOutputStream(ServerConnectClientThreadManager.getServerConnectClientThread(userId).getSocket().getOutputStream());
                    oos.writeObject(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            IMServer.getOfflineDB().get(userId).clear();
            System.out.println(userId + " offline messages is reading over and clear!");
        }

        label:
        //this label for ending the while loop
        while (true) {
            System.out.println("Server and Client " + userId + " keep correspondence...");

            try {
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                /*
                store the offline message into offline database if receiver is not online
                add the message into receiver's ArrayList<Message>;
                 */
                boolean receiverIsOnline = ServerConnectClientThreadManager.checkUserIsOnline(message.getReceiver());

                //check the message type
                switch (message.getMsgType()) {
                    case MessageType.MESSAGE_GET_ONLINE_USERS_LIST:
                        //client request to get online users list
                        System.out.println(message.getSender() + " request to get online users list");
                        String onlineUsersList = ServerConnectClientThreadManager.getOnlineUsersList();
                        //create a message object contains onlineUsersList
                        Message responseMsg = new Message();
                        responseMsg.setMsgType(MessageType.MESSAGE_RETURN_ONLINE_USERS_LIST);
                        responseMsg.setContent(onlineUsersList);
                        //now message receiver is before sender
                        responseMsg.setReceiver(message.getSender());

                        //write the response message to receiver client
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(responseMsg);
                        break;

                    case MessageType.MESSAGE_PRIVATE_CHAT:
                    /*
                    private chat and file transmit have same the function actually
                    the server pass the private chat message and file message from one client to other client
                     */
                    case MessageType.MESSAGE_FILE:

                        if (receiverIsOnline) {
                            oos = new ObjectOutputStream(ServerConnectClientThreadManager.getServerConnectClientThread(message.getReceiver()).getSocket().getOutputStream());
                            oos.writeObject(message);
                        } else {
                            IMServer.getOfflineDB().get(message.getReceiver()).add(message);
                            System.out.println("offline message is stored for " + message.getReceiver());
                        }
                        break;

                    case MessageType.MESSAGE_PUBLIC_CHAT:
                    /*
                    get public chat message from client
                    send this message to every online users
                     */
                        HashMap<String, ServerConnectClientThread> hm = ServerConnectClientThreadManager.getHashMap();
                        Set<String> keys = hm.keySet();
                        for (String key : keys) {
                            if (key.equals(message.getSender())) {
                                continue;
                            }
                            //hm.get(key); return a thread of client
                            oos = new ObjectOutputStream(ServerConnectClientThreadManager.getServerConnectClientThread(key).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                        break;

                    case MessageType.MESSAGE_CLIENT_EXIT:
                        System.out.println(message.getSender() + " logout and exit the system");
                    /*
                    server receive message that client is logout
                    server do action to treat this event
                     */
                        ServerConnectClientThreadManager.removeServerConnectClientThread(message.getSender());
                        socket.close();
                        //exit the thread while loop
                        break label;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
