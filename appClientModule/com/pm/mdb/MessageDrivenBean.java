package com.pm.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import java.util.logging.Logger;

@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"), @ActivationConfigProperty(
						propertyName = "destination", propertyValue = "queue/msgQueue")
		}, 
		mappedName = "msgQueue")

public class MessageDrivenBean implements MessageListener {
	
    public void onMessage(Message message) {
    	TextMessage text = (TextMessage) message;
        try {
        		System.out.println(text.getText());
		} catch (JMSException e) {
			
			e.printStackTrace();
		}
        
    }

}
