package com.amazon.ask.helloworld.handlers;

import static com.amazon.ask.helloworld.handlers.OtpIntentHandler.OTP_KEY;
import static com.amazon.ask.request.Predicates.intentName;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.helloworld.util.BaseMethods;
import com.amazon.ask.helloworld.util.table;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;;


public class EmailIntentHandler implements RequestHandler
{

    public static final String EMAIL_KEY = "EMAIL";
    public static final String EMAIL_SLOT = "Email";
	@Override
	public boolean canHandle(HandlerInput input) 
	{
		return input.matches(intentName("EmailIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) 
	{
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();
		
		Slot email = slots.get(EMAIL_SLOT);
		String speechText , repromptText;
		String email1 = email.getValue();
		email1 = email1.replace(" ", "@");
		String finalemail = email1.toLowerCase();
		System.out.println("Email:"+finalemail);
		
		if(isValidEmailAddress(finalemail))
		{
			input.getAttributesManager().setSessionAttributes(Collections.singletonMap(EMAIL_KEY, finalemail));
			System.out.println("Above Speech text");
			speechText = String.format("Your Email is %s. You get O.T.P. from us.Plz check it.", finalemail);
            repromptText = "You are welcome..";
    		
            System.out.println("Above otp");
    		String value =  table.search(finalemail);
    		System.out.println("Value:"+value);
    		
    		if(value.equals(finalemail))
            {
        		System.out.println("In otp");
            
        		String otp = BaseMethods.generatePassword();
            	
        		System.out.println("OTP:"+otp);
            	System.out.println("Mail:"+finalemail);

        		System.out.println("Above send mail");

        		BaseMethods.sendMail(finalemail, otp);
        		System.out.println("Below send mail.");            	
            	
            	
        		System.out.println("Above session");
       	
    			input.getAttributesManager().setSessionAttributes(Collections.singletonMap(OTP_KEY, otp));
            	
        		System.out.println("Above final session");            	
            	
        		System.out.println("OTP:"+otp);
            }
		}
		else
		{
            speechText = "Please provide a valid email Please try again.";
            repromptText ="I'm not sure what your email is.";
		}
        return input.getResponseBuilder()
                .withSimpleCard("ColorSession", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .withShouldEndSession(false)
                .build();
	}
	
	public static boolean isValidEmailAddress(String email)
	{
		   boolean result = true;
		   try
		   {
			   InternetAddress emailAddr = new InternetAddress(email);
			   emailAddr.validate();
		   }
		   catch (AddressException ex)
		   {
			   result = false;
		   }
		   return result;
	} 
}
