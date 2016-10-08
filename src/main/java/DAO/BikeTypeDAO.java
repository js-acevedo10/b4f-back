package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import DTO.Bike;
import DTO.BikeType;
import Utilities.BikesDB;
import Utilities.ObjectIdAdapter;
import Utilities.ResponseBiker;

public class BikeTypeDAO {
	
	public static JsonParser jsonParser = new JsonParser();
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	public static Response getBikeTypes() {
		Datastore datastore = BikesDB.getDatastore();
		List<BikeType> types = datastore.createQuery(BikeType.class).asList();
		if (types  == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Error fetching types.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(types, Response.Status.OK);
		}
	}
	
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
			datastore.save(bikeType);
			return getBikeTypes();
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
			Query<Bike> query = BikesDB.getDatastore().find(Bike.class).field("bikeType").equal(BikesDB.getDatastore().get(BikeType.class,new ObjectId(bikeTypeId)));
			if (query.asList().isEmpty()){
				resultBikeType.delete();
				datastore.save(resultBikeType);
				return ResponseBiker.buildResponse("BikeType Deleted", Response.Status.OK);
			}
			else{
				jsonMap.clear();
				jsonMap.put("Error", "You need to delete any bike with this bike type before deleting it");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.CONFLICT);
			}
		}
		
	}
	
	
}