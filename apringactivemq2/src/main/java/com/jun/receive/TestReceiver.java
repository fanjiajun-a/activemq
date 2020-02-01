package com.jun.receive;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

/**
 * @author
 * @date 2020-01-30 13:12
 */
public class TestReceiver {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Receiver receive = (Receiver) context.getBean("receive");
        String s = receive.receive();
        System.out.println(s);

    }
}
