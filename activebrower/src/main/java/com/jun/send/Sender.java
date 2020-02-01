package com.jun.send;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author
 * @date 2020-01-29 13:12
 */
public class Sender {

    public static void main(String[] args) {

        Connection connection = null;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        try {
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue");
            MessageProducer producer = session.createProducer(queue);
            for (int i = 0; i <100 ; i++) {
                TextMessage textMessage = session.createTextMessage("发送得消息为" + i);
                producer.send(textMessage);
            }
            session.commit();
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
