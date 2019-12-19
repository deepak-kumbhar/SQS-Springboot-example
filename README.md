# SQS-Springboot-example - Development
Amazon Simple Queue Service with Spring Boot

## Prerequisites
- Create SQS queue (I am using standard queue)
- Secret key and Access key
- AWS maven dependencies

## How to implement this project
- `git clone https://github.com/deepak-kumbhar/SQS-Springboot-example.git`
- Make changes in application.properties as per your credentials.
- Run as springboot application

## API Reuqest details
### API request to send message on SQS
- URL: http://localhost:8080/sqs/send
- Method: POST
- Body:
  {
	"message":"This message will be appear on SQS and then send back to the Application."
  }
  
## For Receiving message 
#### I have created thread (`NotificationListener.class`) where it will start on every 2 minutes. You can change as per your need.

