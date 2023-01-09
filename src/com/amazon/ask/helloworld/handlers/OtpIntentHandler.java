package com.amazon.ask.helloworld.handlers;

import static com.amazon.ask.helloworld.handlers.EmailIntentHandler.EMAIL_KEY;
import static com.amazon.ask.request.Predicates.intentName;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class OtpIntentHandler implements RequestHandler
{
    public static final String OTP_KEY = "OTP";
    public static final String OTP_SLOT = "Otp";

	@Override
	public boolean canHandle(HandlerInput input) 
	{
		return input.matches(intentName("OtpIntent"));
	}
	
	@Override
	public Optional<Response> handle(HandlerInput input) 
	{
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();
		
		Slot otp = slots.get(OTP_SLOT);
		String speechText = null ,repromptText = null;
		String user_otp = otp.getValue();

		System.out.println("OTP:"+otp);

		String gen_otp = (String)input.getAttributesManager().getSessionAttributes().get(OTP_KEY);
		boolean noAnswerProvided = false;

		System.out.println("first:");

        if ((gen_otp != null) && (user_otp != null)) 
        {
    		System.out.println("second:");

        	if(user_otp.equals(gen_otp))
        	{
        		System.out.println("third:");
        		
                speechText = "Hello \nProceed";
                repromptText ="Hey";
                
        		System.out.println("four");

        	}
    		System.out.println("five");

        }
        else
        {
            speechText ="I'm not sure what your otp is. Plz try again .";
            repromptText ="I'm not sure what your otp is.";
            noAnswerProvided = true;
        }

		System.out.println("Six");


		return input.getResponseBuilder()
                .withSimpleCard("ColorSession", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .withShouldEndSession(!noAnswerProvided)
                .build();
	}

}