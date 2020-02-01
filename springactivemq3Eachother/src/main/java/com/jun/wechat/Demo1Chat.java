package com.jun.wechat;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 这个类是讲述相互聊天
 * @date 2020-01-31 11:28
 */
public class Demo1Chat implements MessageListener {

    private  TopicSession pubSession;
    private  TopicPublisher publisher;
    private TopicConnection connection;
    private  String username;



    public Demo1Chat(String topicFactory,String topicName,String userName) throws NamingException, JMSException {


        //使用jndi配置文件创建一个连接  重要

        InitialContext initialContext = new InitialContext();

        //查找一个jms连接工厂并创建连接
        TopicConnectionFactory lookup = (TopicConnectionFactory) initialContext.lookup(topicFactory);

        TopicConnection connection = lookup.createTopicConnection();

        //创建两个jms会话对象
        TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        //查找一个jms主题

        Topic chatTopic = (Topic) initialContext.lookup(topicName);

        //创建一个jms发布者和一个订阅者，createSubScriber中附加的参数是一个消息
        //选择器（null）和noLocal编辑的一个真值，它表明这个发布者生产的消息不应该被它自己所消费
        TopicPublisher publisher = pubSession.createPublisher(chatTopic);
        TopicSubscriber subscriber = subSession.createSubscriber(chatTopic, null, true);

        //设置一个jms消息侦听器
        subscriber.setMessageListener(this);

        //初始化chat应用程序变量
        this.connection = connection;
        this.publisher=publisher;
        this.pubSession=pubSession;
        this.username=userName;
        //启动jsm连接，允许传递消息
        connection.start();
    }

    @Override
    public void onMessage(Message message) {
        TextMessage message1 = (TextMessage) message;
        try {
            System.out.println(message1.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //使用发布者创建并发送消息
    public  void writeMessage(String text) throws JMSException {

        TextMessage textMessage = pubSession.createTextMessage();
        textMessage.setText("来自"+username+"::>"+text);
        publisher.publish(textMessage);
    }
    //关闭连接
    public void close() throws JMSException {
        connection.close();
    }

    public static void main(String[] args) throws JMSException, NamingException, IOException {

        String topicFactory ="TopicF";
        String topicName="topic1";
        String username="北京";
        Demo1Chat chat = new Demo1Chat(topicFactory, topicName, username);
        System.out.println("这是北京用户名");

        //从命令行读取
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true){

            String readLine = bufferedReader.readLine();
            if (readLine.equals("exit")){
                chat.close();
                System.exit(0);
            }else {
                chat.writeMessage(readLine);
            }
        }

    }









}
