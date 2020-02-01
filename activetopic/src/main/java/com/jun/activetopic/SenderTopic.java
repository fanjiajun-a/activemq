package com.jun.activetopic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author
 * @date 2020-01-29 10:04
 */
public class SenderTopic {

    public static void main(String[] args) {

        //创建工厂
        ConnectionFactory factory = null;
        //创建连接
        Connection connection = null;
        //创建session
        Session session =null;
        //创建队列
        Destination destination = null;
        //创建发送者
        MessageProducer producer =null;

        factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://localhost:61616");


        try {

            connection = factory.createConnection();

            connection.start();

           session =  connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);

           destination = session.createTopic("topic");

           producer = session.createProducer(destination);

           producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            for (int i = 0; i <100 ; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TextMessage textMessage = session.createTextMessage("发送topic" + i);
                System.out.println(textMessage);
                producer.send(textMessage);
            }
            session.commit();

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
}
