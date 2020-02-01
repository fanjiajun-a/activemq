package com.jun.activetopicreceive;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author
 * @date 2020-01-29 10:40
 */
public class ReceiverTopic implements Runnable {

    private String threadName;

    public ReceiverTopic(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {

        Connection connection = null;


        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");

        try {
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("topic");
            MessageConsumer consumer = session.createConsumer(topic);
            while (true){
                TextMessage receive = (TextMessage) consumer.receive();
                if(receive != null){
                    System.out.println("当前线程"+threadName+"发送消息"+receive.getText());
                }else {
                    continue;
                }
            }


        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (connection !=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        ReceiverTopic receiverTopic1 = new ReceiverTopic("receiverTopic1");
        ReceiverTopic receiverTopic2 = new ReceiverTopic("receiverTopic2");
        ReceiverTopic receiverTopic3 = new ReceiverTopic("receiverTopic3");

        Thread thread1 = new Thread(receiverTopic1);
        Thread thread2 = new Thread(receiverTopic2);
        Thread thread3 = new Thread(receiverTopic3);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
