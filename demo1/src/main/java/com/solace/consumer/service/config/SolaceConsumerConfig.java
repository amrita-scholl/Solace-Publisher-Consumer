package com.solace.consumer.service.config;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.*;


@Configuration
public class SolaceConsumerConfig {

    @Value("${solace.broker.host}")
    private String host;

    @Value("${solace.username}")
    private String username;

    @Value("${solace.password}")
    private String password;

    @Value("${solace.queue.name}")
    private String queueName;

    @Value("${solace.java.msg-vpn}")
    private String vpn;

//    @Bean
//    public JMSContext jmsContext() throws Exception {
//        // Create and configure Solace connection factory
//        SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();
//        connectionFactory.setHost(host);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//
//        // Create JMSContext and establish the connection
//        return (JMSContext) connectionFactory.createConnection();
//    }

    @Bean
    public Connection jmsConnection() throws Exception {
        // Create and configure Solace connection factory
        SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVPN(vpn);
        // Create JMS connection and establish the connection
        return connectionFactory.createConnection();
    }

    @Bean public Session jmsSession(Connection connection) throws Exception {

        // Create JMS session from the connection
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    }

    // Queue Bean (Using JMS Session to create Queue)
    @Bean
    public Queue queue(Session jmsSession) throws JMSException {
        // Create and return a Queue object using the JMS session
        return jmsSession.createQueue(queueName);

    }

    // MessageProducer Bean (Using JMS Session to create MessageProducer)
    @Bean
    public MessageProducer messageProducer(Session jmsSession, Queue queue) throws JMSException {
        // Create and return a MessageProducer object
        return jmsSession.createProducer(queue);
    }


    // MessageConsumer Bean (Using JMS Session to create MessageConsumer)
    @Bean
    public MessageConsumer messageConsumer(Session jmsSession, Queue queue) throws JMSException {
        // Create and return a MessageConsumer object to listen to the specified queue
        return jmsSession.createConsumer(queue);
    }
}