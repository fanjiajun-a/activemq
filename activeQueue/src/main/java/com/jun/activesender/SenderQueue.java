package com.jun.activesender;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author
 * @date 2020-01-29 11:12
 */
public class SenderQueue {

    private  static final  String URL = "tcp://localhost:61616";
    private  static  final String messages ="hello,everybody";

    public static void main(String[] args) {

        Connection connection =null;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);
        try {
            connection = factory.createConnection();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue");
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(messages);
            textMessage.setStringProperty("head","body");
            producer.send(textMessage);
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
