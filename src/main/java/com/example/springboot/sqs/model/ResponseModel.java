package com.example.springboot.sqs.model;

import org.springframework.http.HttpStatus;

public class ResponseModel {

	private String error;
	private Object object;
	private String status;
	private String message;
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static ResponseModel getInstance() {
		ResponseModel response = new ResponseModel();
		response.setStatus(HttpStatus.CREATED.toString());
		return response;
	}
}
