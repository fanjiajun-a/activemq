package com.jun.receive;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

/**
 * @author
 * @date 2020-01-30 13:04
 */
public class Receiver {
    public Receiver() {
    }

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public String  receive(){

        String s = "";
        MapMessage receive = (MapMessage) jmsTemplate.receive();

        try {
           s =  receive.getString("firstname");
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return s;
    }
}
