package com.example.springboot.sqs.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.example.springboot.sqs.config.SqsConfig;

@Component
@EnableScheduling
public class NotificationListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class);

	@Value("${sqs.endpoint.url}")
	private String sqsURL;

	@Autowired
	private SqsConfig sqsConfig;

	@Scheduled(cron = "0 */2 * ? * *")
	public void getMessage() {

		final AmazonSQS sqs = sqsConfig.amazonSQSAsync();
		while (true) {
			LOGGER.info("Receiving messages from MyQueue.\n");
			final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(sqsURL)
					.withMaxNumberOfMessages(1).withWaitTimeSeconds(3);
			final List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
			for (final Message message : messages) {
				LOGGER.info("  Body:" + message.getBody());
				if (!"".equals(message.getBody())) {
					// Logic to perform SQS message
					
					//Delete message from SQS.
					LOGGER.info("Deleting a message.\n");
					final String messageReceiptHandle = messages.get(0).getReceiptHandle();
					sqs.deleteMessage(new DeleteMessageRequest(sqsURL, messageReceiptHandle));
				}
			}
		}
	}
}