package com.az.task.config;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@EnableJms
@Configuration
public class JmsConfiguration {

	/*
	@Autowired
	private ConnectionFactory connectionFactory;	*/	

	@Bean
	public MarshallingMessageConverter createMarshallingMessageConverter(final Jaxb2Marshaller jaxb2Marshaller) {
		
		return new MarshallingMessageConverter(jaxb2Marshaller);
	}
	
	@Bean
	public Jaxb2Marshaller createJaxb2Marshaller(@Value("${jaxb.package}") final String packageToScane) {		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();		
		jaxb2Marshaller.setPackagesToScan(packageToScane);
		Map<String, Object> properties = new HashMap<>();
		properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxb2Marshaller.setMarshallerProperties(properties);		
		return jaxb2Marshaller;
	}
	
	@Bean
	public JmsTemplate jmsTemplate(final MarshallingMessageConverter marshallingMessageConverter,ConnectionFactory connectionFactory){
	    JmsTemplate template = new JmsTemplate();
	    template.setConnectionFactory(connectionFactory);	    
	    template.setMessageConverter(marshallingMessageConverter);
	    /*
	     * By default jmsTemplate transmit to Queues by having pubSubDomain set to false, 
	       As per requirement I need to transmit messages to Topic. so I make pubSubDomain set to true,
	       link :https://spring.io/guides/gs/messaging-jms/
	        
	     */      
	    template.setPubSubDomain(true);
	    return template;
	}

	
}
