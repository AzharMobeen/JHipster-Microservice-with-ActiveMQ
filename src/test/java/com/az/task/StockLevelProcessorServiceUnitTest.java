package com.az.task;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;

import org.junit.Before;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.az.task.domain.ControlSegment;
import com.az.task.domain.UCStockLevelIFD;
import com.az.task.service.StockLevelProcessorService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StockLevelProcessorServiceUnitTest{			

	private static final Logger log = LoggerFactory.getLogger(StockLevelProcessorServiceUnitTest.class);
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
	private StockLevelProcessorService stockLevelProcessorService;			
	
	private UCStockLevelIFD stockLevel;
	
	@Before
	public void configurations() {						
		this.jmsTemplate = new JmsTemplate(this.connectionFactory);		
		this.jmsTemplate.setMessageConverter(marshallingMessageConverter);
		this.prepareDummyData();
	}

    /*
     * For consumer test I need to publish message to ("source") ActiveMQ-Queues,
     * then it will consume from the same queue and send to ("destination")
     * ActiveMQ-Topics. For this purpose, I'm using JAXB2 marshaller for both
     * inbound and outbound data.
     */
    @Test
    public void consumerFromQueuesAndSendToTopic() {

        this.jmsTemplate.convertAndSend(source, stockLevel, message -> {
            if (message instanceof BytesMessage) {
                BytesMessage messageBody = (BytesMessage) message;
                messageBody.reset();
                Long length = messageBody.getBodyLength();
                log.info("***** MSG LENGTH is {} bytes", length);
                byte[] byteMyMessage = new byte[length.intValue()];
                messageBody.readBytes(byteMyMessage);
                log.info("***** \n<!-- XML MSG START -->\n{}\n<!-- XML MSG END -->", new String(byteMyMessage));
            }
            return message;
        });

        // With In three seconds. @JmsListener from StockLevelProcessorService will consume message.
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }

        UCStockLevelIFD receivedStockLevel = this.stockLevelProcessorService.getStockLevel();
        assertNotNull("In Task Component We are setting this object after publishing to Topics", receivedStockLevel);
        assertThat(receivedStockLevel).isEqualTo(stockLevel);
    }
    @Test
    public void messageSendToTopicsQueue() {
        this.stockLevelProcessorService.sendToDestinationTopicQueue(stockLevel);;
        assertNotNull(this.stockLevelProcessorService.getStockLevel());
        assertThat(this.stockLevelProcessorService.getStockLevel()).isEqualTo(stockLevel);
    }

    /*
     * This method is to prepare some dummy data for this test
     */
	private void prepareDummyData() {
		UCStockLevelIFD stockLevel = new UCStockLevelIFD();		
		ControlSegment ctrlSeg = new ControlSegment("UU_SSSS_LEVEL","20180100",
				"0de01919-81eb-4cc7-a51d-15f6085fc1a4","WHHHH","CLI","xxxx",
				"bcccc8-5a07-4hi6-8yyy-8290d3ccfb51","6543");		
		stockLevel.setCtrlSegList(Arrays.asList(ctrlSeg));				
		this.stockLevel = stockLevel;
	}
	
}
