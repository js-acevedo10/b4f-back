package DAO;

import java.util.HashMap;
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

public class BikeTypeDao {
	
	public static void main(String[] args) {

		//  TEST getBikeType 
//
//      System.out.println(getBikeTypeWithName("Standard").getEntity().toString());
//		
//		
		// TEST addBikeType
		
//		BikeType p = new BikeType("Mascot", 2);
//		System.out.println(addBikeType(p).getEntity().toString());
//		
		
		// TEST editBikeType
		// Poner ID de un bike type de la tabla
//		Datastore datastore = BikesDB.getDatastore();
//		final Query<BikeType> queryBikeType = datastore.createQuery(BikeType.class);
//		queryBikeType.field("id").equal(new ObjectId("57ce309b8e2eaa2d0ff3e1f4"));
//		BikeType bikeType = queryBikeType.get();
//		bikeType.setName("Pet");
//		
//        System.out.println(editBikeType(bikeType).getEntity().toString());
		
		// TEST DeleteBikeType
		//Cambiar ID por uno de la tabla...
//		System.out.println(deleteBikeType("57ce23078e2eaa24d922c772").getEntity().toString());
//		
//		
	}
	
	public static JsonParser jsonParser = new JsonParser();
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	
	public static Response getBikeTypeWithName(String bikeTypeName) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<BikeType> queryBikeType = datastore.createQuery(BikeType.class);
		queryBikeType.field("name").equal(bikeTypeName);
		BikeType bikeType = queryBikeType.get();
		if (bikeType  == null) {
			jsonMap.clear();
			jsonMap.put("Error", "BikeType not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(bikeType, Response.Status.OK);
		}
	}
	
	
	public static Response addBikeType(BikeType bikeType) {
		
		Datastore datastore = BikesDB.getDatastore();
		final Query<BikeType> queryBikeType = datastore.createQuery(BikeType.class);
		queryBikeType.field("name").equal(bikeType.name);
		BikeType bikeTypeFound = queryBikeType.get();
		if (bikeTypeFound  == null) {
			BikesDB.getDatastore().save(bikeType);
			return ResponseBiker.buildResponse(bikeType, Response.Status.OK);
		} else {
			jsonMap.clear();
			jsonMap.put("Error", "BikeType name already taken");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.CONFLICT);
		}
	}
	
	public static Response editBikeType(BikeType bikeType) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<BikeType> queryBikeType = datastore.createQuery(BikeType.class);
		queryBikeType.field("id").equal(bikeType.id);
		BikeType resultBikeType = queryBikeType.get();
		if (resultBikeType == null) {
			jsonMap.clear();
			jsonMap.put("Error", "BikeType not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			datastore.save(bikeType);
			return ResponseBiker.buildResponse(bikeType, Response.Status.OK);
		}
		
		
	}
	
	public static Response deleteBikeType(String bikeTypeId) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<BikeType> queryBikeType = datastore.createQuery(BikeType.class);
		queryBikeType.field("id").equal(new ObjectId(bikeTypeId));
		BikeType resultBikeType = queryBikeType.get();
		if (resultBikeType == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Biketype not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			datastore.delete(resultBikeType);
			return ResponseBiker.buildResponse("BikeType Deleted", Response.Status.OK);
		}
		
	}
	
	
}