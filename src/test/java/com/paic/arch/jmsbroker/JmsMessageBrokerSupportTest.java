package com.paic.arch.jmsbroker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

public class JmsMessageBrokerSupportTest {
	private static final Logger logger = getLogger(JmsMessageBrokerSupportTest.class);
	
    public static final String TEST_QUEUE = "MY_TEST_QUEUE";
    public static final String MESSAGE_CONTENT = "Lorem blah blah";
    private static JmsMessageBrokerSupport JMS_SUPPORT;
    private static String REMOTE_BROKER_URL;

    @BeforeClass
    public static void setup() throws Exception {
        JMS_SUPPORT = JmsMessageBrokerSupport.createARunningEmbeddedBrokerOnAvailablePort();
        REMOTE_BROKER_URL = JMS_SUPPORT.getBrokerUrl();
    }

    @AfterClass
    public static void teardown() throws Exception {
        JMS_SUPPORT.stopTheRunningBroker();
    }

    @Test
    public void sendsMessagesToTheRunningBroker() throws Exception {
        JmsMessageBrokerSupport.bindToBrokerAtUrl(REMOTE_BROKER_URL)
                .andThen().sendATextMessageToDestinationAt(TEST_QUEUE, MESSAGE_CONTENT);
        long messageCount = JMS_SUPPORT.getEnqueuedMessageCountAt(TEST_QUEUE);
        assertThat(messageCount).isEqualTo(1);
        logger.info("Send msg : {}" , MESSAGE_CONTENT);
    }

    @Test
    public void readsMessagesPreviouslyWrittenToAQueue() throws Exception {
        String receivedMessage = JmsMessageBrokerSupport.bindToBrokerAtUrl(REMOTE_BROKER_URL)
                .sendATextMessageToDestinationAt(TEST_QUEUE, MESSAGE_CONTENT)
                .andThen().retrieveASingleMessageFromTheDestination(TEST_QUEUE);
        assertThat(receivedMessage).isEqualTo(MESSAGE_CONTENT);
        logger.info("Recevie msg : {}" , MESSAGE_CONTENT);
    }

    @Test(expected = JmsMessageBrokerServiceImpl.NoMessageReceivedException.class)
    public void throwsExceptionWhenNoMessagesReceivedInTimeout() throws Exception {
        JmsMessageBrokerSupport.bindToBrokerAtUrl(REMOTE_BROKER_URL).retrieveASingleMessageFromTheDestination(TEST_QUEUE, 1);
    }


}