package com.az.task;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.az.task.service.TaskService;
import com.az.task.model.CtrlSeg;
import com.az.task.model.StockLevel;




@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties= {"spring.activemq.broker-url=vm://localhost?broker.persistent=false&broker.useShutdownHook=false",
		"spring.activemq.in-memory=true"})
public class IntegrationTests{

    private static final Logger log = LoggerFactory.getLogger(IntegrationTests.class);

	@ClassRule
	public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();	
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private MarshallingMessageConverter marshallingMessageConverter;
	
	@Value("${outbound.endpoint}")
	private String destination;
	
	@Value("${inbound.endpoint}")
	private String source;		
	private JmsTemplate jmsTemplate;		
	
	@Autowired
	private TaskService taskService;
	
	private StockLevel stockLevel;
	
	@Before
	public void configurations() {
		connectionFactory = broker.createConnectionFactory();		
		this.jmsTemplate = new JmsTemplate(this.connectionFactory);		
		this.jmsTemplate.setMessageConverter(marshallingMessageConverter);
		this.prepareDummyData();				
	}	
	
	
	@Test
	public void consumerFromQueuesAndSendToTopic() {
		
		// For consume test I need to Publish message to ("source") ActiveMQ-Queues 
		// Then it will consume from same Queue and Send to ("destination") ActiveMQ-Topics
		// I'm using JAXB2 marshaller for both inbound and outbound data. 
		
		this.jmsTemplate.convertAndSend(source,stockLevel,
		        new MessagePostProcessor() {
	          @Override
	          public Message postProcessMessage(Message message)
	              throws JMSException {
	            if (message instanceof BytesMessage) {
	              BytesMessage messageBody = (BytesMessage) message;
	              // message is in write mode, close & reset to start
	              // of byte stream
	              messageBody.reset();
	 
	              Long length = messageBody.getBodyLength();
	              log.debug("***** MSG LENGTH is {} bytes",
	                  length);
	              
	              byte[] byteMyMessage = new byte[length.intValue()];
	              messageBody.readBytes(byteMyMessage);
	              log.debug(
	                  "***** \n<!-- XML MSG START -->\n{}\n<!-- XML MSG END -->",
	                  new String(byteMyMessage));
	            }
	            return message;
	          }
	        });
		
		// With In three seconds. @JmsListener from TaskComponent will consume message.
		
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {	
			log.error(e.toString());
		}
        StockLevel stockLevel2 =this.taskService.getStockLevel();
        assertNotNull("In Task Component We are setting this object after publishing to Topics", stockLevel2);
        // We can also compare some properties 
        assertThat(stockLevel2).isEqualTo(stockLevel);   
	}		
	
	// This method is to prepare some dummy data for this test
	private void prepareDummyData() {
		StockLevel stockLevel = new StockLevel();		
		CtrlSeg ctrlSeg = new CtrlSeg("UU_SSSS_LEVEL","20180100",
				"0de01919-81eb-4cc7-a51d-15f6085fc1a4","WHHHH","CLI","xxxx",
				"bcccc8-5a07-4hi6-8yyy-8290d3ccfb51","6543");		
		stockLevel.setCtrlSegList(Arrays.asList(ctrlSeg));				
		this.stockLevel = stockLevel;
	}

	
}
