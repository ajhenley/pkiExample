package com.example.demo.crypt.utils;

import jdk.nashorn.internal.objects.annotations.Getter;

public class EncryptedMessage {
    private String encryptedMessagePayload;
    private String senderId;
    private String encryptedSenderId;
    private String messageDigest;

    public EncryptedMessage() {
    }

    public EncryptedMessage(String encryptedMessagePayload, String senderId, String encryptedSenderId, String messageDigest) {
        this.encryptedMessagePayload = encryptedMessagePayload;
        this.senderId = senderId;
        this.encryptedSenderId = encryptedSenderId;
        this.messageDigest = messageDigest;
    }

    public void compromisedEncryptedMessagePayload(String message)
    {
        this.encryptedMessagePayload = message;
    }

    @Override
    public String toString()
    {
        return encryptedMessagePayload;
    }

    public String getEncryptedMessagePayload() {
        return encryptedMessagePayload;
    }

    public void setEncryptedMessagePayload(String encryptedMessagePayload) {
        this.encryptedMessagePayload = encryptedMessagePayload;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getEncryptedSenderId() {
        return encryptedSenderId;
    }

    public void setEncryptedSenderId(String encryptedSenderId) {
        this.encryptedSenderId = encryptedSenderId;
    }

    public String getMessageDigest() {
        return messageDigest;
    }

    public void setMessageDigest(String messageDigest) {
        this.messageDigest = messageDigest;
    }
}
