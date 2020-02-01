package com.jun.com.jun.receive;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author
 * @date 2020-01-29 13:17
 */
public class Receiver {

    public static void main(String[] args) {

        Connection connection = null;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        try {
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue");
            MessageConsumer consumer = session.createConsumer(queue);
            while (true) {
                TextMessage receive = (TextMessage) consumer.receive();
                if(receive != null) {
                    System.out.println(receive.getText());
                }else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
