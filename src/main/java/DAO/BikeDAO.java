package DAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import DTO.Bike;
import DTO.BikeType;
import DTO.RentPlace;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class BikeDAO {
	
	public static JsonParser jsonParser = new JsonParser();
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	public static Response getBikes() {
		Datastore datastore = BikesDB.getDatastore();
		List<Bike> bikes = datastore.createQuery(Bike.class).asList();
		if (bikes  == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Error fetching bikes");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(bikes, Response.Status.OK);
		}
	}
	
	public static Response getBikeWithId(String bikeId) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<Bike> queryBike = datastore.createQuery(Bike.class);
		queryBike.field("id").equal(new ObjectId(bikeId));
		Bike bike = queryBike.get();
		if (bike  == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Bike not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(bike, Response.Status.OK);
		}
	}
	
	public static Response getBikesWithRentId(String rentId) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<Bike> queryBike = datastore.createQuery(Bike.class);
		queryBike.field("history").hasThisElement(datastore.get(RentPlace.class, new ObjectId(rentId)));
		List<Bike> bikes = queryBike.asList();
		if (bikes == null || bikes.isEmpty()) {
			jsonMap.clear();
			jsonMap.put("Error", "Bikes not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(bikes, Response.Status.OK);
		}
	}
	
	public static Response addBike(Bike bike, String typeId, String venueId) {
		Datastore datastore = BikesDB.getDatastore();
		BikeType bikeType = datastore.get(BikeType.class, new ObjectId(typeId));
		RentPlace venue = datastore.get(RentPlace.class, new ObjectId(venueId));
		if (bikeType == null || venue == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Arguments mismatch");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			bike.setBikeType(bikeType);
			bike.addRentPlace(venueId);
			datastore.save(bike);
			venue.addBike(bike);
			datastore.save(venue);
			return getBikes();
		}
		
	}
	
	public static Response editBike(Bike bike) {
		Datastore datastore = BikesDB.getDatastore();
		Bike resultBike = datastore.get(Bike.class, bike.id);
		if (resultBike == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Bike not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			datastore.save(bike);
			return ResponseBiker.buildResponse(bike, Response.Status.OK);
		}
		
		
	}
	
	public static Response deleteBike(String bikeId) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<Bike> queryBike = datastore.createQuery(Bike.class);
		queryBike.field("id").equal(new ObjectId(bikeId));
		Bike resultBike = queryBike.get();
		if (resultBike == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Bike not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			resultBike.delete();
			datastore.save(resultBike);
			return ResponseBiker.buildResponse("Bike Deleted", Response.Status.OK);
		}
		
	}

	public static Response reserveBikeWithId(String bikeId) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<Bike> queryBike = datastore.createQuery(Bike.class);
		queryBike.field("id").equal(new ObjectId(bikeId));
		Bike resultBike = queryBike.get();
		if (resultBike == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Bike not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			resultBike.setReserveDate(new Date());
			datastore.save(resultBike);
			return ResponseBiker.buildResponse(resultBike, Response.Status.OK);
		}
	}
	
	
}