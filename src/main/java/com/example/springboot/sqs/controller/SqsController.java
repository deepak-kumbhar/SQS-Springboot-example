package com.example.springboot.sqs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.springboot.sqs.config.SqsConfig;
import com.example.springboot.sqs.model.ResponseModel;

@RestController
@RequestMapping("/sqs")
public class SqsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqsController.class);

	@Value("${sqs.endpoint.url}")
	private String sqsURL;

	@Autowired
	private SqsConfig sqsConfig;

	@PostMapping(value = "/send")
	public ResponseEntity<ResponseModel> sendMessageToSQS(@RequestBody ResponseModel sqsObject) {
		ResponseModel responseModel = new ResponseModel();
		try {
			final AmazonSQS sqs = sqsConfig.amazonSQSAsync();
			LOGGER.info("Sending a message to Queue.\n");
			sqs.sendMessage(new SendMessageRequest(sqsURL, sqsObject.getMessage()));
			LOGGER.info("Message Sent.\n");
			responseModel.setMessage("Message sent successfully on SQS.");
		} catch (final AmazonServiceException ase) {
			LOGGER.error(" Caught amazon service exception: \n");
			LOGGER.error("Error Message:    " + ase.getMessage());
			LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
			LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
			LOGGER.error("Error Type:       " + ase.getErrorType());
			LOGGER.error("Request ID:       " + ase.getRequestId());
			responseModel.setError(ase.getMessage());
			responseModel.setMessage("Something went wrong");
		} catch (final AmazonClientException ace) {
			LOGGER.error(" Something went wrong");
			LOGGER.error("Error Message: " + ace.getMessage());
			responseModel.setError(ace.getMessage());
			responseModel.setMessage("Something went wrong");
		}
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
