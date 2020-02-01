package com.jun.receive;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author
 * @date 2020-01-31 14:20
 */
public class Receiver {

    public static void main(String[] args) {

        ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                        ActiveMQConnectionFactory.DEFAULT_PASSWORD,"failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61617)");

        try {
            Connection connection = factory.createConnection();

            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue junge = session.createQueue("junge");
            MessageConsumer consumer = session.createConsumer(junge);


            while (true){

                TextMessage receive = (TextMessage) consumer.receive();
                if(receive != null){
                    System.out.println("收到的消息"+receive.getText());
                }else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
