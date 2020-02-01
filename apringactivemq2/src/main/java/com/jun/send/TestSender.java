package com.jun.send;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

/**
 * @author
 * @date 2020-01-30 13:13
 */
public class TestSender {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Sender send = (Sender) context.getBean("send");

        send.send();
    }
}
