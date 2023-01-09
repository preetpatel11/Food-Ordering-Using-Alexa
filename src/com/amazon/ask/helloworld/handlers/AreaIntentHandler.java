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

public class AreaIntentHandler implements RequestHandler {

	public static final String AREA_KEY = "AREA";
	public static final String AREA_SLOT = "AreaName";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AreaIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println("Start");
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		DataBaseHandler jdbc = new DataBaseHandler();

		Slot area = slots.get(AREA_SLOT);
		String speechText, repromptText;
		String area1 = area.getValue();
		String finalArea = area1.toLowerCase();
		System.out.println("Area:" + finalArea);

		System.out.println("ABove method call");
		List ls = jdbc.areaRestaurantSearch(finalArea);
		System.out.println("Name................" + ls);
		int i;
		String names = "";
		int x = ls.size();
		for (i = 0; i < x; i++) {
			names = names + ls.get(i);
			if (i < (x - 1)) {
				names = names + ",";
			}
		}
		speechText = String.format(
				"You selected %s area and here are the restaurants %s which are near to area you can selecte restaurant by saying 'select xyz'",
				finalArea, names);
		repromptText = "You are welcome..";

		return input.getResponseBuilder().withSimpleCard("Area", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(false).build();
	}

}
