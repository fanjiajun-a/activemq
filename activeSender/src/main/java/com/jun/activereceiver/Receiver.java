package com.jun.activereceiver;

import org.apache.activemq.ActiveMQConnectionFactory;


import javax.jms.*;

/**
 * @author
 * @date 2020-01-27 12:44
 */
public class Receiver {

    public static void main(String[] args) {

       Connection connection = null;

        //创建消息对象
        ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");

        //创建连接
        try {
            connection = factory.createConnection();

            //启动连接
            connection.start();
            //创建session
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            //创建队列
            Queue queue = session.createQueue("junge");

            //创建消费者
            MessageConsumer consumer = session.createConsumer(queue);

            //接受消息

            while (true){

                TextMessage receive = (TextMessage) consumer.receive();
                if(receive != null){
                    System.out.println("接收得消息为"+receive);

                }else {
                    break;
                }
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
