package com.jun.receive;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author
 * @date 2020-01-30 12:20
 */
public class TestReceiver {


    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");


        Receiver receive = (Receiver) context.getBean("receive");

        receive.receive();


    }
}
