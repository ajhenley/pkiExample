package com.example.demo;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long sender;
    private Long recipient;

    @Column(length=2048)
    private String body;

    @Lob
    @Column(length=8192)
    private String encryptedMessage;

    public Message() {
    }

    public Message(Long sender, Long recipient, String body, String encryptedMessage) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.encryptedMessage = encryptedMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public void setEncryptedMessage(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }
}
