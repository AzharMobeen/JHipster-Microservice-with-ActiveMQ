package com.az.task.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.az.task.model.StockLevel;



@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${outbound.endpoint}")
	private String destination;
	
	private StockLevel stockLevel;

	
	@JmsListener(destination="${inbound.endpoint}")
	public void process(StockLevel stockLevel) {
		if(stockLevel!=null) {
			log.debug(":::: Message Recieved :::: ");
			this.displayResult(stockLevel);			
			this.sendToDestinationTopicQueue(stockLevel);
			// For Unit-Testing purpose
			this.setStockLevel(stockLevel);
		}
		else
			log.error("stockLevel :: Null");
	}
	
	private void displayResult(StockLevel stockLevel) {
		if(!CollectionUtils.isEmpty(stockLevel.getCtrlSegList()))
			stockLevel.getCtrlSegList().forEach(ctrlSeg->{
				log.debug(":::: "+ ctrlSeg.toString()+ " :::: ");
			});
		else
			log.error("stockLevel.getCtrlSegList :: null");
	}
	
	public void sendToDestinationTopicQueue(StockLevel stockLevel) {
		log.debug("sendToDestinationTopicQueue method Start");
		this.jmsTemplate.convertAndSend(destination, stockLevel);
		log.debug("Message successfully published on Topic-Queue");
	}

    public StockLevel getStockLevel() {
        return stockLevel;
    }
    public void setStockLevel(StockLevel stockLevel) {
        this.stockLevel = stockLevel;
    }
}
