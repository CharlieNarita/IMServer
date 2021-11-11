package com.charlie.imserver.service;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.charlie.imcommon.*;

/**
 * server listening at designate port
 * and waiting for client connection
 * always keep up correspondence
 *
 * @author AC
 * @version 1.0
 * @date 10/14/2021
 */
public class IMServer {
    private ServerSocket serverSocket = null;
    /*
    create a collection, store valid users
    if login user information match these stored users
    the connection will establish
    note: HashMap is not thread safe
    recommend to use ConcurrentHashMap, it is synchronized so thread safe
     */
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, ArrayList<Message>> offlineDB = new ConcurrentHashMap<>();

    /*
    static code block init valid users.
    attention! static code will initialize with IMServer initialization once time.
     */
    static {
        validUsers.put("admin", new User("admin", "88888"));
        validUsers.put("100", new User("100", "12345"));
        validUsers.put("200", new User("200", "12345"));
        validUsers.put("300", new User("300", "12345"));
        validUsers.put("dog", new User("dog", "12345"));
        validUsers.put("cat", new User("cat", "12345"));
    }

    static {
        offlineDB.put("admin", new ArrayList<Message>());
        offlineDB.put("100", new ArrayList<Message>());
        offlineDB.put("200", new ArrayList<Message>());
        offlineDB.put("300", new ArrayList<Message>());
        offlineDB.put("dog", new ArrayList<Message>());
        offlineDB.put("cat", new ArrayList<Message>());
    }

    public IMServer() {
        initServer();
    }

    /**
     * verify the userId and password
     *
     * @param userId
     * @param password
     * @return
     */
    private boolean verifyUser(String userId, String password) {
        boolean isValid = true;
        //return a user object if userId exist as a key in validUsers collection
        User user = validUsers.get(userId);
        if (user == null) {
            System.out.println("user is not exist");
            isValid = false;
        } else if (!password.equals(user.getPassword())) {
            System.out.println("user password is wrong");
            isValid = false;
        }
        return isValid;
    }

    /**
     * initialize the server start
     */
    private void initServer() {
        //tips: port can write in config file
        System.out.println("Server is listening at port " + 9999);

        /*
        news feeds service thread
        this individual thread is running for public news feeds
         */
        new Thread(new SendNewsToAllService()).start();

        /*
        initialize the server socket
        make server listening at designate port
         */
        try {
            serverSocket = new ServerSocket(9999);
            /*
            listening action is continuing
            so properly use while loop
             */
            while (true) {
                /*
                always ready for reading object from client
                note: accept() method is blocking method
                 */
                Socket accept = serverSocket.accept();

                //read User object from client for verify
                ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
                User user = (User) ois.readObject();

                //verify user and return result as true/false
                boolean isValid = verifyUser(user.getUserId(), user.getPassword());

                //prepare Message object witch contains user verify result to reply client
                ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream());
                Message message = new Message();

                //init the verification result information in message object then write it to client
                if (isValid) {
                    message.setMsgType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //reply message to client
                    oos.writeObject(message);

                    //create thread which hold socket object to connect with client
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(accept, user.getUserId());
                    serverConnectClientThread.start();

                    //add these threads into HashMap to manage when user verification result is valid
                    ServerConnectClientThreadManager.addServerConnectClientThread(user.getUserId(), serverConnectClientThread);
                    System.out.println(user.getUserId() + " is saved into thread hashmap");


                } else {
                    System.out.println("User id = " + user.getUserId() + " password = " + user.getPassword() + " verification is fail...");
                    message.setMsgType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    oos.close();
                    ois.close();
                    accept.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static ConcurrentHashMap<String, User> getValidUsers() {
        return validUsers;
    }

    public static ConcurrentHashMap<String, ArrayList<Message>> getOfflineDB() {
        return offlineDB;
    }
}
