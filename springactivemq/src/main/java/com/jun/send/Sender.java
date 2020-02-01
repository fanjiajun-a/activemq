package com.jun.send;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author
 * @date 2020-01-30 11:36
 */
public class Sender {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplates");

        Destination destination = (Destination) context.getBean("destination");

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("你好，我是activeMq");
            }
        });
        System.out.println("success");
    }
}
