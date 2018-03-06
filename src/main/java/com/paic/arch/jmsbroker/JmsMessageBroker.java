package com.paic.arch.jmsbroker;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 连接工厂类
* @create by xiaoluhuan
 */
public class JmsMessageBroker {
	
	private String brokerUrl;
	private BrokerService brokerService;
	private ActiveMQConnectionFactory connectionFactory;
	
	public JmsMessageBroker(String brokerUrl) {
		super();
		this.brokerUrl = brokerUrl;
		connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
	}
	
	public String getBrokerUrl() {
		return brokerUrl;
	}
	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}
	public BrokerService getBrokerService() {
		return brokerService;
	}
	public void setBrokerService(BrokerService brokerService) {
		this.brokerService = brokerService;
	}
	public ActiveMQConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	public void setConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	

}
