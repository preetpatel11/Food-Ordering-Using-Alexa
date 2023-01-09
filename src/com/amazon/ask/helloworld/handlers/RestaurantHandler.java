package com.amazon.ask.helloworld.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dao.DataBaseHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class RestaurantHandler implements RequestHandler {

	public static final String RESTAURANT_KEY = "RESTAURANT";
	public static final String RESTAURANT_SLOT = "RestaurantName";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("RestaurantIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println("Start restaurant");
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		DataBaseHandler jdbc = new DataBaseHandler();

		Slot restaurant = slots.get(RESTAURANT_SLOT);
		String speechText, repromptText;
		String restaurant1 = restaurant.getValue();
		String finalRestaurant = restaurant1.toLowerCase();
		System.out.println("Area:" + finalRestaurant);
		
		System.out.println("ABove method call");
		List ls = jdbc.restaurantData(finalRestaurant);
		System.out.println("Product................" + ls);
		int i;
		String names = "";
		int x = ls.size();
		for (i = 0; i < x; i++) {
			names = names + ls.get(i);
			if (i < (x - 1)) {
				names = names + ",";
			}
		}
		speechText = "Hello";
		repromptText = "You are welcome..";

		return input.getResponseBuilder().withSimpleCard("Restaurant", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(false).build();
	}

}
