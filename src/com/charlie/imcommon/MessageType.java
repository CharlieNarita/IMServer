package com.charlie.imcommon;

/**
 * define message types
 *
 * @author AC
 * @version 1.0
 * @date 10/13/2021
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1"; //represent login succeed
    String MESSAGE_LOGIN_FAIL = "2"; //represent login fail
    String MESSAGE_PUBLIC_CHAT = "3";    //public message
    String MESSAGE_GET_ONLINE_USERS_LIST = "4";  //request server return online users list
    String MESSAGE_RETURN_ONLINE_USERS_LIST = "5"; //server return online users list
    String MESSAGE_CLIENT_EXIT = "6";   //client request to exit
    String MESSAGE_PRIVATE_CHAT = "7";   //client request to private chat
    String MESSAGE_FILE = "8";  //file transmission
}
