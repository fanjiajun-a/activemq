package com.jun.activereceiver;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.sound.midi.Soundbank;

/**
 * @author
 * @date 2020-01-29 11:23
 */
public class ReceiverQueue {

    private  static final  String URL = "tcp://localhost:61616";
    private  static  final String messages ="hello,everybody";

    public static void main(String[] args) {

        Connection connection = null;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);
        try {

            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue");
            MessageConsumer consumer = session.createConsumer(queue);
            while (true){

                TextMessage receive = (TextMessage) consumer.receive();
                if(receive != null){
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
