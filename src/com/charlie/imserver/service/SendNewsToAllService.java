package com.charlie.imserver.service;

import com.charlie.imcommon.Message;
import com.charlie.imcommon.MessageType;
import com.charlie.imserver.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author AC
 * @version 1.0
 * @date 10/18/2021
 */
public class SendNewsToAllService implements Runnable {
    boolean loop = true;

    @Override
    public void run() {
        while (loop) {
            System.out.println("Please enter server news[Enter \"exit\" to exit news service]: ");
            String newsContent = Utility.readString(256);
            if ("exit".equals(newsContent)) {
                loop = false;
                System.out.println("exit the news feeds server!");
                break;
            }
            //create a message
            Message serverNews = new Message();
            serverNews.setMsgType(MessageType.MESSAGE_PUBLIC_CHAT);
            serverNews.setSender("Server");
            serverNews.setContent(newsContent);
            serverNews.setSendTime(new Date().toString());
            System.out.println("Server say to everyone: " + newsContent);

            //traverse all threads get sockets then send message
            HashMap<String, ServerConnectClientThread> hm = ServerConnectClientThreadManager.getHashMap();
            ObjectOutputStream oos = null;
            Set<Map.Entry<String, ServerConnectClientThread>> entries = hm.entrySet();
            for (Map.Entry<String, ServerConnectClientThread> entry : entries) {
                try {
                    oos = new ObjectOutputStream(entry.getValue().getSocket().getOutputStream());
                    oos.writeObject(serverNews);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
