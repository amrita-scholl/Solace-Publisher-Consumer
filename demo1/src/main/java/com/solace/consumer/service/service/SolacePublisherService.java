package com.solace.consumer.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class SolacePublisherService {

    private final Connection connection;
    private final Queue queue;

    // Autowired to inject the connection and queue (use @Bean configuration to provide these)
    @Autowired
    public SolacePublisherService(Connection connection, Queue queue) {
        this.connection = connection;
        this.queue = queue;
    }

    // Send message method using the provided Connection and Queue
    public void sendMessage(String messageBody) {
        try {
            // Create a session from the connection
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a message producer
            MessageProducer producer = session.createProducer(queue);

            // Create a text message
            TextMessage textMessage = session.createTextMessage(messageBody);

            // Send the message
            producer.send(textMessage);

            // Log the message sent
            System.out.println("Message sent to Solace queue: " + messageBody);

            // Clean up
            producer.close();
            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

}
