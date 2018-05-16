package com.example.demo;

import javax.persistence.*;
import java.security.PrivateKey;
import java.security.PublicKey;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Lob
    @Column(length=100000)
    private PrivateKey privateKey;

    @Lob
    @Column(length=100000)
    private PublicKey publicKey;
    private Long classid;


    public Student() {
    }

    public Student(String name, PrivateKey privateKey, PublicKey publicKey, Long classid) {
        this.name = name;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.classid = classid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClassid() {
        return classid;
    }

    public void setClassid(Long classid) {
        this.classid = classid;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
