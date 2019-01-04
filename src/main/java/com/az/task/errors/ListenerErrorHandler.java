package com.az.task.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;




public class ListenerErrorHandler implements ErrorHandler{
	private static final Logger log = LoggerFactory.getLogger(ListenerErrorHandler.class);
	@Override
	public void handleError(Throwable t) {
		log.warn("\n\tListener is Unable to unmarshal the Message");
        log.error("\n\t"+t.getCause().getMessage());
		
	}

}
