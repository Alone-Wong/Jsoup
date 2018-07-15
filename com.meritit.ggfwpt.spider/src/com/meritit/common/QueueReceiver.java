package com.meritit.common;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.log4j.Logger;

import com.meritit.common.util.GetTmax;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.WeatherCrawler;
import com.meritit.customize.thread.HYXXThread;

public class QueueReceiver  {
	
	private static String time;
	
	
	protected static Logger logger = Logger.getLogger(QueueReceiver.class);
	static Properties ip=PropertyUtils.loadProp("url");
	
	private static final String AMQIP = ip.getProperty("ip");

	
	
	Connection connection = null;
    Session session ;
	
	
	//创建一个连接工厂
	private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(AMQIP);
	
	@SuppressWarnings("null")
	public String getMsg() throws JMSException {
		
		
		
		try {	
			//从工厂中创建一个连接（连接对象封装了与JMS提供者之间的虚拟连接）
			connection = connectionFactory.createConnection();
			//开启连接
			connection.start();
			
			//创建一个session
			//对消息进行操作的接口，提供了事务的功能，如果需要使用session发送/接收多个消息时，可以将这些发送/接收动作放到一个事务中
			//可以通过session创建生产者、消费者、消息等
			session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
			
			//目的地指明消息被发送的目的地以及客户端接受消息的来源
			Destination destination = session.createQueue("time2-queue");

			//Consumer：消息生产者由Session创建，用于往目的地发送消息
			ActiveMQMessageConsumer consumer=(ActiveMQMessageConsumer) session.createConsumer(destination);
		
			Message msg=null;
			
			TextMessage textmessage=(TextMessage) consumer.receive();
			logger.info("接收到数据库中的数据为+"+textmessage.getText());
			
			if(null!=textmessage){
				String insertDate=textmessage.getText();
				time=insertDate;
				logger.info(time);
			}
			
			textmessage.acknowledge();
		//	session.commit();
			
			
		}catch (JMSException e) {
			e.printStackTrace();
		}finally{
			if(session != null){
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	
		return time;
	}

	
	
	
}