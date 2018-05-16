package com.example.demo;

import com.example.demo.crypt.utils.EncryptedMessage;
import com.example.demo.encryptor.RsaEncryptor;
import com.example.demo.exception.PayloadAndDigestMismatchException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;
import java.util.Set;

public class RsaMessenger {

    private final RsaEncryptor encryptionHandler;
    private final Set<Person> trustedContacts;
    private final String personId;

    public RsaMessenger(Set<Person> trustedContacts, String personId) {
        this.encryptionHandler = new RsaEncryptor();
        this.trustedContacts = trustedContacts;
        this.personId = personId;
    }

    public PublicKey getPublicKey() {
        return this.encryptionHandler.getPublicKey();
    }


    public PrivateKey getPrivateKey() {
        return this.encryptionHandler.getPrivateKey();
    }

    public EncryptedMessage encryptMessageForPerson(String message, Person person) {
        String encryptedMessage = this.encryptionHandler.encryptMessageForPublicKeyOwner(message, person.getPublicKey());
        String myEncryptedId = this.encryptionHandler.encryptMessageWithPrivateKey(this.personId);
        String hashedMessage = this.encryptionHandler.hashMessage(message);
        return new EncryptedMessage(encryptedMessage, this.personId, myEncryptedId, hashedMessage);
    }

    public String readEncryptedMessage(EncryptedMessage message) {
        String decryptedMessage = this.encryptionHandler.decryptReceivedMessage(message);
        Optional<Person> sender = tryIdentifyMessageSender(message.getSenderId());

        if(!decryptedMessageHashIsValid(decryptedMessage, message.getMessageDigest())) {
            return "Message digest sent does not match the one generated from the received message";
            //throw new PayloadAndDigestMismatchException(
            //        "Message digest sent does not match the one generated from the received message");
        }

        if(sender.isPresent() && senderSignatureIsValid(sender.get(), message.getEncryptedSenderId())) {
            System.out.println(sender.get().getName() +" send message: " + decryptedMessage);
            return sender.get().getName() +" send message: " + decryptedMessage;
        }else {
            System.out.println("Unknown source send message: " + decryptedMessage);
            return "Unknown source send message: " + decryptedMessage;
        }
    }

    private boolean senderSignatureIsValid(Person sender, String encryptedSenderId) {
        if(rawSenderIdMatchesDecryptedSenderId(sender, encryptedSenderId)) {
            return true;
        }

        return false;
    }

    private boolean rawSenderIdMatchesDecryptedSenderId(Person sender, String encryptedSenderId) {
        return sender.getId().equals(
                this.encryptionHandler.decryptMessageFromOwnerOfPublicKey(encryptedSenderId, sender.getPublicKey()));
    }

    private Optional<Person> tryIdentifyMessageSender(String id) {
        return this.trustedContacts.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst();
    }

    private boolean decryptedMessageHashIsValid(String decryptedMessage, String hashedMessage) {
        String decryptedMessageHashed = this.encryptionHandler.hashMessage(decryptedMessage);
        if(decryptedMessageHashed.equals(hashedMessage)) {
            return true;
        }

        return false;
    }
}
