package com.meritit.common;


import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

import com.meritit.common.util.PropertyUtils;

public class QueueSender {
	
	static Properties ip=PropertyUtils.loadProp("url");
	
	private static final String AMQIP = ip.getProperty("ip");
	
	private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(AMQIP);
	
	/**
	 * 发送信息
	 * @param message
	 */
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
	
	/**
	 * 发送查询时间信息
	 * @param message
	 */
	public static void sendTMsg(String message) {
		Connection connection = null;
		Session session = null;
		try {
			System.out.println(message);
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);

			Destination destination = session.createQueue("time-queue");

			ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session
					.createProducer(destination);
			 producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			 
			TextMessage textmessage = session.createTextMessage(message);
			System.out.println("cahxu"+message);

			
			producer.send(textmessage);
			
//			session.commit();
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
	
//	public static void main(String args[]) throws JMSException{
//		 
//		QueueSender.sendTMsg("1511328912263-001");
//		
//		QueueReceiver q=new QueueReceiver();
//		
//		System.out.println("sadadd"+q.getMsg());
//		
//		
//	
//	}
	
}