package com.jun.send;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.security.PrivateKey;

/**
 * @author
 * @date 2020-01-31 14:12
 */
public class Sender {

    public static void main(String[] args) {

        Connection connection = null;

        ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                        ActiveMQConnectionFactory.DEFAULT_PASSWORD,"failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61617)");

        try {
            connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue junge = session.createQueue("junge");

            MessageProducer producer = session.createProducer(junge);

            for (int i = 0; i <1000 ; i++) {

                TextMessage textMessage = session.createTextMessage("发出的消息,第" + i + "条");
                System.out.println(">>>"+textMessage.getText());
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
