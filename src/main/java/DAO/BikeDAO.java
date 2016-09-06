package DAO;

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
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class BikeDAO {
	
	public static void main(String[] args) {

		//  TEST getBike 
//      Meter un ID de la tabla
//      System.out.println(getBikeWithId("57ce235b8e2eaa25153992a4").getEntity().toString());
		
		
		// TEST addBike
		
//		Bike p = new Bike("TRX", null, false, true);
//		System.out.println(addBike(p, "Standard").getEntity().toString());
//		
		
		// TEST editBike
		// Poner ID de una bicicleta de la tabla
//		Datastore datastore = BikesDB.getDatastore();
//		final Query<Bike> queryBike = datastore.createQuery(Bike.class);
//		queryBike.field("id").equal(new ObjectId("57ce22b48e2eaa2462313b2f"));
//		Bike bike = queryBike.get();
//		bike.setBrand("NEW");
//		
//		System.out.println(editBike(bike).getEntity().toString());
		
		// TEST DeleteBike
		//Cambiar ID por uno de la tabla...
//		System.out.println(deleteBike("57ce23078e2eaa24d922c772").getEntity().toString());
//		
//		
	}
	
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
	
	
	public static Response addBike(Bike bike, String bikeTypeId) {
		
		Datastore datastore = BikesDB.getDatastore();
		BikeType bikeType = datastore.get(BikeType.class, bikeTypeId);
		if (bikeType  == null) {
			jsonMap.clear();
			jsonMap.put("Error", "BikeType not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			BikesDB.getDatastore().save(bike);
			bike.setBikeType(bikeType);
			return getBikes();
		}
	}
	
	public static Response editBike(Bike bike) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<Bike> queryBike = datastore.createQuery(Bike.class);
		queryBike.field("id").equal(bike.id);
		Bike resultBike = queryBike.get();
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
			datastore.delete(resultBike);
			return ResponseBiker.buildResponse("Bike Deleted", Response.Status.OK);
		}
		
	}
	
	
}