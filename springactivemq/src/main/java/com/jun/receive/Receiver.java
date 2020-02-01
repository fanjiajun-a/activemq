package com.jun.receive;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author
 * @date 2020-01-30 11:40
 */
public class Receiver {

    public Receiver() {
    }

    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void receive(){

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Destination destination = (Destination) context.getBean("destination");



        while (true){
            TextMessage receive = (TextMessage) jmsTemplate.receive(destination);
            try {
                if (receive != null){
                     System.out.println("接收的消息为"+receive);

                    System.out.println(receive.getText());

                 }else {
                break;
            }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
