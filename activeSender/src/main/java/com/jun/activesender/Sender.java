package com.jun.activesender;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.awt.*;

/**
 * @author
 * @date 2020-01-27 11:56
 */
public class Sender {

    public static void main(String[] args) {

        Connection connection=null;

        //创建工厂对象
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory
                (ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");


        try {
            //创建连接
            connection = factory.createConnection();
            //启动连接
            connection.start();

            //创建session  第一个参数是是否支持事务，第二个是自动连接
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //创建队列
            Queue queue = session.createQueue("junge");

            //创建生产者
            MessageProducer producer = session.createProducer(queue);

            //设置消息的持久化以及非持久化
            //消息持久化方式：AMQ,kahaDB;JDBC,leveldb;

            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            //发送消息
            for (int i = 0; i <100 ; i++) {

                TextMessage textMessage = session.createTextMessage("发送得消息为:" + i);
                System.out.println(textMessage);
                producer.send(textMessage);

            }

            session.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(connection!= null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
