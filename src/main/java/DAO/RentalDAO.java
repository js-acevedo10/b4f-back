package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.mongodb.morphia.Datastore;

import com.google.gson.Gson;

import DTO.Rental;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class RentalDAO {
	
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	public static Response getRentals() {
		Datastore datastore = BikesDB.getDatastore();

		List<Rental> rentals = datastore.createQuery(Rental.class).asList();

		if(rentals == null || rentals.isEmpty()) {
			jsonMap.clear();
			jsonMap.put("Error", "No rentals found.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(rentals, Response.Status.OK);
		}
	}
}
