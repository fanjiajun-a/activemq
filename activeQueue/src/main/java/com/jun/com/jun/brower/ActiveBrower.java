package com.jun.com.jun.brower;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Enumeration;

/**
 * @author
 * @date 2020-01-29 12:51
 */
public class ActiveBrower {


    public static void main(String[] args) {

        Connection connection = null;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        try {
            connection = factory.createConnection();

            connection.start();
            Session session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue");
            QueueBrowser browser = session.createBrowser(queue);
            Enumeration enumeration = browser.getEnumeration();
            while (enumeration.hasMoreElements()){
                TextMessage message = (TextMessage) enumeration.nextElement();
                System.out.println(message.getText());
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
