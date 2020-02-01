package com.jun.wechat;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author
 * @date 2020-01-31 12:10
 */
public class Demo2Chat implements MessageListener{

    private TopicConnection connection;
    private TopicPublisher publisher;
    private TopicSession session;
    private String userName;

    public Demo2Chat(String topicFactory,String topicName,String userName) throws NamingException, JMSException {
        //使用jndi创建一个连接
        InitialContext initialContext = new InitialContext();

        //创建工厂并连接
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory) initialContext.lookup(topicFactory);

        TopicConnection connection = connectionFactory.createTopicConnection();

        //创建两个会话
        TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        //查找主题

        Topic topic = (Topic) initialContext.lookup(topicName);


        //创建一个jms发布者和一个订阅者，createSubScriber中附加的参数是一个消息
        //选择器（null）和noLocal编辑的一个真值，它表明这个发布者生产的消息不应该被它自己所消费

        TopicPublisher publisher = pubSession.createPublisher(topic);
        TopicSubscriber subscriber = subSession.createSubscriber(topic, null, true);

        //创建jms监听
        subscriber.setMessageListener(this);

        this.connection=connection;
        this.publisher=publisher;
        this.session=pubSession;
        this.userName=userName;

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

    //发布消息
    public  void writeMessage(String text) throws JMSException {

        TextMessage textMessage = session.createTextMessage("来自" + userName + "::>" + text);
        publisher.publish(textMessage);

    }
    //关闭
    public void close() throws JMSException {
        connection.close();
    }

    public static void main(String[] args) throws IOException, JMSException, NamingException {

        String topicFactory = "TopicF";
        String topicName = "topic1";
        String userName = "天津";
        System.out.println("这是天津用户名");


        Demo2Chat chat = new Demo2Chat(topicFactory, topicName, userName);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


        while (true){
            String readLine = bufferedReader.readLine();
            if(readLine.equals("exit")){
                chat.close();
                System.exit(0);
            }else {
                chat.writeMessage(readLine);
            }
        }


    }

}
