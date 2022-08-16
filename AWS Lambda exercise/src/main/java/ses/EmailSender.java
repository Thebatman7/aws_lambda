package ses;

// these are the imports for SDK v1
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.Regions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class EmailSender {

    public EmailResult handleRequest(EmailRequest request, Context context) {

        EmailResult myEmailResult = new EmailResult();

        LambdaLogger logger = context.getLogger();
        logger.log("Entering send_email");

        try {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_2).build();

            //AmazonSimpleEmailService object sends email messages using the values in the EmailRequest parameter object
            String source = request.getFrom();
            //List<String> addresses = new ArrayList<>();
            //addresses.add(request.getTo());
            Destination destination = new Destination().withToAddresses(request.getTo());
            Content content = new Content(request.getSubject());
            Content bodycontent = new Content(request.getTextBody());
            Body body = new Body(bodycontent);
            body.setHtml(new Content(request.getHtmlBody()));
            //Message(Content subject, Body body)
            Message message = new Message(content, body);
            //SendEmailRequest(String source, Destination destination, Message message)
            SendEmailRequest sendEmailRequest = new SendEmailRequest(source, destination, message);


            SendEmailResult emailResult;
            emailResult = client.sendEmail(sendEmailRequest);


            myEmailResult.setMessage(emailResult.toString());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            now.format(dtf);

            String timestamp = now.toString();
            myEmailResult.setTimestamp(timestamp);

            logger.log("Email sent!");
        } catch (Exception ex) {
            logger.log("The email was not sent. Error message: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        finally {
            logger.log("Leaving send_email");
        }

        return myEmailResult;
    }

}


