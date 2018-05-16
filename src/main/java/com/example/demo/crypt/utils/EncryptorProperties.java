package com.example.demo.crypt.utils;


public class EncryptorProperties {
    private final AsymmetricAlgorithm asymmetricAlgorithm;
    private final int keyLength;

    public EncryptorProperties(AsymmetricAlgorithm asymmetricAlgorithm, int keyLength) {
        this.asymmetricAlgorithm = asymmetricAlgorithm;
        this.keyLength = keyLength;
    }

    public String getAsymmetricAlgorithm(){
        return asymmetricAlgorithm.toString();
    }
    public int getKeyLength() {
        return keyLength;
    }

}
