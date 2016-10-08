package DAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import DTO.Bike;
import DTO.BikeType;
import DTO.Client;
import DTO.Penalty;
import DTO.RentPlace;
import DTO.Rental;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class RentPlaceDAO {
	public static JsonParser jsonParser = new JsonParser();
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	public static Response getRentPlaces() {
		Datastore datastore = BikesDB.getDatastore();

		List<RentPlace> places = datastore.createQuery(RentPlace.class)
					.asList();

		if(places == null || places.isEmpty()) {
			jsonMap.clear();
			jsonMap.put("Error", "No rent places found.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(places, Response.Status.OK);
		}
	}
	public static Response getRentPlaceWithId(String placeId) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<RentPlace> queryPlace = datastore.createQuery(RentPlace.class);
		queryPlace.field("id").equal(new ObjectId(placeId));
		RentPlace place = queryPlace.get();
		if (place  == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Bike not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(place, Response.Status.OK);
		}
	}
	
	
	public static Response addRentPlace(RentPlace place) {
		BikesDB.getDatastore().save(place);
		return ResponseBiker.buildResponse(place, Response.Status.OK);
	}
	
	public static Response editRentPlace(RentPlace place) {
		Datastore datastore = BikesDB.getDatastore();
		System.out.println("hola - " + place.getId());
		RentPlace resultBike = datastore.get(RentPlace.class, place.getId());
		if (resultBike == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Rent Place not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			datastore.save(place);
			return ResponseBiker.buildResponse(place, Response.Status.OK);
		}
		
		
	}
	
	public static Response deleteRentPlace(String placeId) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<RentPlace> placeQuery = datastore.createQuery(RentPlace.class);
		placeQuery.field("id").equal(new ObjectId(placeId));
		RentPlace resultPLace = placeQuery.get();
		if (resultPLace == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Rent Place not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			resultPLace.delete();
			datastore.save(resultPLace);
			return ResponseBiker.buildResponse("Rent Place Deleted", Response.Status.OK);
		}
		
	}
	public static Response rentBike(Document rentInfo)
	{
		System.out.println(rentInfo);
		String placeName = rentInfo.getString("venueName");
		String bikeId = rentInfo.getString("bikeId"); 
		String userMail = rentInfo.getString("userMail");
		
		Datastore datastore = BikesDB.getDatastore();
		RentPlace place = datastore.createQuery(RentPlace.class).field("name").equal(placeName).get();
		if (place == null){
			jsonMap.clear();
			jsonMap.put("Error", "Rent Place not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		}
		Bike bike = datastore.get(Bike.class, new ObjectId(bikeId));
		if (bike == null){
			jsonMap.clear();
			jsonMap.put("Error", "Bike not found in this Rent Place");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		}
		Client client = datastore.createQuery(Client.class).field("email").equal(userMail).get();
		if (client == null){
			jsonMap.clear();
			jsonMap.put("Error", "User not found.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		}
		if (client.isSuspended()){
			jsonMap.clear();
			jsonMap.put("Error", "Client is suspended.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.CONFLICT);
		}
		
		place.removeBike(bike);
		datastore.save(place);
		
		Rental rent = new Rental(bike, client, new Date(), place);
		rent.create();
		datastore.save(rent);
		bike.addRental(rent.getId().toHexString());
		bike.setAvailable(false);
		datastore.save(bike);
		
		return ResponseBiker.buildResponse("Rent Place rented bike: "+bikeId, Response.Status.OK);
	
	}
//	public static Response returnBike(String placeId, String bikeId)
//	{
//		Datastore datastore = BikesDB.getDatastore();
//		RentPlace place = datastore.createQuery(RentPlace.class)
//				.field("id").equal(placeId)
//				.get(); 
//		if (place == null){
//			jsonMap.clear();
//			jsonMap.put("Error", "Rent Place not found");
//			String error = g.toJson(jsonMap);
//			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
//		}
//		Bike bike = datastore.createQuery(Bike.class)
//				.field("id").equal(bikeId)
//				.get(); 
//		if (bike == null){
//			jsonMap.clear();
//			jsonMap.put("Error", "Bike not found");
//			String error = g.toJson(jsonMap);
//			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
//		}
//		place.addBike(bike);
//		datastore.save(place);
//
//		return ResponseBiker.buildResponse("Rent Place gets bike: "+bikeId, Response.Status.OK);
//	}
	
	
}
