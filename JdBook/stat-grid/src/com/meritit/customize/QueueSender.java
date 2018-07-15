package com.meritit.customize;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.MessageTransformer;

import com.alibaba.fastjson.JSON;

public class QueueSender {
	private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
			"tcp://191.168.1.32:61616");
	
	public static void sendMsg(String message) {
		Connection connection = null;
		Session session = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue("data-queue");

			ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session
					.createProducer(destination);

			TextMessage textmessage = session.createTextMessage(message);
			

			producer.send(textmessage);
			
			session.commit();
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void sendFilesMsg(String msg){
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection();
			conn.start();
			session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("file-queue");
			ActiveMQMessageProducer producer = (ActiveMQMessageProducer)session.createProducer(destination);
			Message txtMsg = session.createTextMessage(msg);
			producer.send(txtMsg);
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			if(session != null){
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String args[]){
		QueueSender queueSender = new QueueSender();
		Map<String, String> map = new HashMap<String, String>();
		map.put("filename", "ddfsdfdffdfd");
		map.put("fileid", "dfgdfgsdfsdfgsdfwerte");
		String json = JSON.toJSONString(map);
		queueSender.sendFilesMsg(json);
	}
	
}