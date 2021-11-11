package com.charlie.imcommon;

import java.io.Serializable;

/**
 * represent client and server communication message
 *
 * @author AC
 * @version 1.0
 * @date 10/13/2021
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1799000730065787651L;
    private String sender;
    private String receiver;
    private String content;
    private String sendTime;
    private String msgType; //define message types in interface

    //extension of file type message
    private byte[] fileBytes;
    private int fileLen = 0;
    private String src;
    private String dest;

    public Message(String sender, String receiver, String content, String sendTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = sendTime;
    }

    public Message() {
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}
