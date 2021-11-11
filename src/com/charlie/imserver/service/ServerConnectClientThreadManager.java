package com.charlie.imserver.service;

import java.util.HashMap;

/**
 * @author AC
 * @version 1.0
 * @date 10/14/2021
 */
public class ServerConnectClientThreadManager {
    /*
    init a HashMap container for storing threads
    the key is userId, the value is ServerConnectClientThread object
     */
    public static HashMap<String, ServerConnectClientThread> hashMap = new HashMap<>();

    /**
     * add the threads into HashMap
     *
     * @param userId
     * @param serverConnectClientThread
     */
    public static void addServerConnectClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hashMap.put(userId, serverConnectClientThread);
    }

    /**
     * get thread object from HashMap
     *
     * @param userId
     * @return
     */
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hashMap.get(userId);
    }

    public static String getOnlineUsersList() {
        //traverse the map collection to get all elements
        StringBuilder builder = new StringBuilder();
        for (String s : hashMap.keySet()) {
            builder.append(s).append(" ");
        }
        return builder.toString();
    }

    public static void removeServerConnectClientThread(String userId) {
        hashMap.remove(userId);
    }

    public static HashMap<String, ServerConnectClientThread> getHashMap() {
        return hashMap;
    }

    public static boolean checkUserIsOnline(String userId) {
        return hashMap.containsKey(userId);
    }
}
