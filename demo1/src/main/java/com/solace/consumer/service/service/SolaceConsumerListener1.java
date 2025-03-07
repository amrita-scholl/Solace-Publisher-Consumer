package com.solace.consumer.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class SolaceConsumerListener1 {

    @JmsListener(destination = "${solace.queue.name}", containerFactory = "myFactory")
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String messageBody = textMessage.getText();
                System.out.println("Listener 1 received message: " + messageBody);
                // You can process the message here
            }
        } catch (Exception e) {
            System.out.println("Error in Listener 1: " + e.getMessage());
        }
    }
}

