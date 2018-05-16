package com.example.demo;

import com.example.demo.crypt.utils.EncryptedMessage;
import com.example.demo.encryptor.RsaEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String index(){
        String ALICE_MESSAGE_TO_BOB = "Hello Bob";
        String PAULS_MESSAGE_TO_BOB = "Hey there Bob";

        Person bob = new Person("Bob");
        Person alice = new Person("Alice");
        Person paul = new Person("Paul");
        EncryptedMessage alicesEncryptedMessageToBob;
        EncryptedMessage paulsEncryptedMessageToBob;

        bob.addTrustedContact(alice);
        alicesEncryptedMessageToBob = alice.sendEncryptedMessageToPerson(ALICE_MESSAGE_TO_BOB, bob);
        paulsEncryptedMessageToBob = paul.sendEncryptedMessageToPerson(PAULS_MESSAGE_TO_BOB, bob);

        return "index";
    }

    @ResponseBody
    @RequestMapping("/bobReadAlice")
    public String bobReadAlice() {
        String ALICE_MESSAGE_TO_BOB = "Hello Bob";
        String PAULS_MESSAGE_TO_BOB = "Hey there Bob";

        Person bob = new Person("Bob");
        Person alice = new Person("Alice");
        Person paul = new Person("Paul");
        EncryptedMessage alicesEncryptedMessageToBob;
        EncryptedMessage paulsEncryptedMessageToBob;

        bob.addTrustedContact(alice);
        alicesEncryptedMessageToBob = alice.sendEncryptedMessageToPerson(ALICE_MESSAGE_TO_BOB, bob);
        paulsEncryptedMessageToBob = paul.sendEncryptedMessageToPerson(PAULS_MESSAGE_TO_BOB, bob);

        return bob.readEncryptedMessage(alicesEncryptedMessageToBob);

    }

    @ResponseBody
    @RequestMapping("/paulReadAlice")
    public String paulReadAlice() {
        String ALICE_MESSAGE_TO_BOB = "Hello Bob";
        String PAULS_MESSAGE_TO_BOB = "Hey there Bob";

        Person bob = new Person("Bob");
        Person alice = new Person("Alice");
        Person paul = new Person("Paul");
        EncryptedMessage alicesEncryptedMessageToBob;
        EncryptedMessage paulsEncryptedMessageToBob;

        bob.addTrustedContact(alice);
        alicesEncryptedMessageToBob = alice.sendEncryptedMessageToPerson(ALICE_MESSAGE_TO_BOB, bob);
        paulsEncryptedMessageToBob = paul.sendEncryptedMessageToPerson(PAULS_MESSAGE_TO_BOB, bob);

        return paul.readEncryptedMessage(alicesEncryptedMessageToBob);

    }

    @ResponseBody
    @RequestMapping("/bobReadPaul")
    public String bobReadPaul() {
        String ALICE_MESSAGE_TO_BOB = "Hello Bob";
        String PAULS_MESSAGE_TO_BOB = "Hey there Bob";

        Person bob = new Person("Bob");
        Person alice = new Person("Alice");
        Person paul = new Person("Paul");
        EncryptedMessage alicesEncryptedMessageToBob;
        EncryptedMessage paulsEncryptedMessageToBob;

        bob.addTrustedContact(alice);
        alicesEncryptedMessageToBob = alice.sendEncryptedMessageToPerson(ALICE_MESSAGE_TO_BOB, bob);
        paulsEncryptedMessageToBob = paul.sendEncryptedMessageToPerson(PAULS_MESSAGE_TO_BOB, bob);

        return bob.readEncryptedMessage(paulsEncryptedMessageToBob);


    }

    @ResponseBody
    @RequestMapping("/detectChangedMessage")
    public String detectChangedMessage() {
        String ALICE_MESSAGE_TO_BOB = "Hello Bob";
        String PAULS_MESSAGE_TO_BOB = "Hey there Bob";

        Person bob = new Person("Bob");
        Person alice = new Person("Alice");
        Person paul = new Person("Paul");
        EncryptedMessage alicesEncryptedMessageToBob;
        EncryptedMessage paulsEncryptedMessageToBob;

        bob.addTrustedContact(alice);
        alicesEncryptedMessageToBob = alice.sendEncryptedMessageToPerson(ALICE_MESSAGE_TO_BOB, bob);
        paulsEncryptedMessageToBob = paul.sendEncryptedMessageToPerson(PAULS_MESSAGE_TO_BOB, bob);

        EncryptedMessage slightlyDifferentMessage = alice.sendEncryptedMessageToPerson(ALICE_MESSAGE_TO_BOB + " ", bob);
        alicesEncryptedMessageToBob.compromisedEncryptedMessagePayload(slightlyDifferentMessage.getEncryptedMessagePayload());

        return bob.readEncryptedMessage(alicesEncryptedMessageToBob);
    }

    @ResponseBody
    @RequestMapping("/storeKeys")
    public String testDB(){
        RsaEncryptor encryptionHandler;
        Set<Person> mycontacts = new HashSet<>();
        RsaMessenger rsaMessenger = new RsaMessenger(new HashSet<>(), "1");
        PublicKey publicKey = rsaMessenger.getPublicKey();
        PrivateKey privateKey = rsaMessenger.getPrivateKey();
        Student student = new Student();

        student.setPrivateKey(privateKey);
        student.setPublicKey(publicKey);
        student.setName("Bob");
        student.setClassid(1L);
        studentRepository.save(student);

        rsaMessenger = new RsaMessenger(new HashSet<>(), "2");
        publicKey = rsaMessenger.getPublicKey();
        privateKey = rsaMessenger.getPrivateKey();
        Student student1 = new Student();
        student1.setPrivateKey(privateKey);
        student1.setPublicKey(publicKey);
        student1.setName("Sarah");
        student1.setClassid(1L);
        studentRepository.save(student1);
        return "Saved";
    }

    @ResponseBody
    @RequestMapping("/privateKey")
    public String privateKey(){
        Student student = studentRepository.findById(1L).get();
        return student.getPrivateKey().toString();
    }

    @ResponseBody
    @RequestMapping("/publicKey")
    public String publicKey(){
        Student student = studentRepository.findById(1L).get();
        return student.getPublicKey().toString();
    }

    @ResponseBody
    @RequestMapping("/sendMessage")
    public String sendMessage(){
        Message message = new Message();
        message.setSender(1L);
        message.setRecipient(2L);

        Student student = studentRepository.findById(1L).get();
        String mess = "This is the way that the message looks.";
        message.setBody(mess);
        RsaEncryptor encryptionHandler = new RsaEncryptor();
        String encMess = encryptionHandler.encryptMessageForPublicKeyOwner(mess, student.getPublicKey());
        message.setEncryptedMessage(encMess);
        messageRepository.save(message);
        return encMess;
    }

    @ResponseBody
    @RequestMapping("/getMessage")
    public String getMessage(){
        String message = "";
        RsaEncryptor encryptionHandler = new RsaEncryptor();
        Iterable<Message> messages = messageRepository.findAll();
        for (Message mess : messages){
            message = mess.getEncryptedMessage();
        }
        Student student = studentRepository.findById(1L).get();

        String decryptedMessage = encryptionHandler.decryptText(message, student.getPrivateKey());
        return decryptedMessage;
    }

}