package DAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import com.google.gson.Gson;

import DTO.Client;
import DTO.RentPlace;
import DTO.Rental;
import DTO.Reparacion;
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
	
	public static Response getRentals(String clientId) {
		Datastore datastore = BikesDB.getDatastore();
		Client client = datastore.get(Client.class, new ObjectId(clientId));
		
		if (client == null){
			jsonMap.clear();
			jsonMap.put("Error", "User not found.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		}
		
		List<Rental> rentals = datastore.createQuery(Rental.class).field("client").equal(client).asList();

		if(rentals == null || rentals.isEmpty()) {
			jsonMap.clear();
			jsonMap.put("Error", "No rentals found for this user.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(rentals, Response.Status.OK);
		}
	}
	
	public static Response returnBike(Document returnInfo) {
		Datastore datastore = BikesDB.getDatastore();
		
		String rentalId = returnInfo.getString("rental");
		String venueId = returnInfo.getString("returnPoint");
		Rental rental = datastore.get(Rental.class, new ObjectId(rentalId));
		RentPlace venue = datastore.get(RentPlace.class, new ObjectId(venueId));

		
		if(rental == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Rental not found.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else if(venue == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Venue not found.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		}else {
			String returningMail = returnInfo.getString("mail");
			if (returningMail.equals(rental.getClient().getEmail()) || rental.getAllowedUsers().contains(returningMail)){

				Double fee = 0.0;
				
				List<String> damaged =  (List<String>) returnInfo.get("selected");
				
				for (String fix_id : damaged){
					fee+=datastore.get(Reparacion.class, new ObjectId(fix_id)).getFee();
				}
//				if (returnInfo.getBoolean("pedals", false)){
//					fee+=18000;
//				}
//				if (returnInfo.getBoolean("saddle", false)){
//					fee+=25000;
//				}
//				if (returnInfo.getBoolean("handlebars", false)){
//					fee+=6000;
//				}
//				if (returnInfo.getBoolean("brakes", false)){
//					fee+=200000;
//				}
//				if (returnInfo.getBoolean("shifter", false)){
//					fee+=30000;
//				}
//				if (returnInfo.getBoolean("holder", false)){
//					fee+=18000;
//				}
//				if (returnInfo.getBoolean("frontGrid", false)){
//					fee+=24000;
//				}
//				if (returnInfo.getBoolean("backGrid", false)){
//					fee+=24000;
//				}
//				if (returnInfo.getBoolean("reflective", false)){
//					fee+=6000;
//				}
//				if (returnInfo.getBoolean("plate", false)){
//					fee+=30000;
//				}
//				if (returnInfo.getBoolean("bikeBacking", false)){
//					fee+=15000;
//				}
//				if (returnInfo.getBoolean("frontFender", false)){
//					fee+=16000;
//				}
//				if (returnInfo.getBoolean("backFender", false)){
//					fee+=16000;
//				}
//				if (returnInfo.getBoolean("frame", false)){
//					fee+=130000;
//				}
//				if (returnInfo.getBoolean("octopus", false)){
//					fee+=8000;
//				}
//				if (returnInfo.getBoolean("handle", false)){
//					fee+=48000;
//				}
				jsonMap.clear();
				jsonMap.put("Fee", ""+fee);
				
				Client client = rental.getClient();
				if (fee>0){
					client.setPoints(client.getPoints() - 100);
				}
				
				if (rental.getRentDate().getTime() + (1000*3600*6) < new Date().getTime()){
					jsonMap.put("Penalized", "true");
					client.setSuspended(true);
					if (rental.getRentDate().getTime() + (1000*3600*12) > new Date().getTime()){
						client.setPoints(client.getPoints() - 5);
					}
					else{
						client.setPoints(client.getPoints() - 500);
					}
					datastore.save(rental.getClient());

				}else{
					jsonMap.put("Penalized", "false");
					client.setPoints(client.getPoints() + 0.5);
				}
				
				client.modify();
				client.setSuspendAfter(null);
				datastore.save(client);
				rental.getBike().setAvailable(true);
				if(returnInfo.getBoolean("mantenimiento")) {
					rental.getBike().setMantenimiento(true);
					rental.getBike().setAvailable(false);
				}
				rental.getBike().setReserve(false);
				rental.getBike().modify();
				rental.setDrop(venue);
				rental.modify();
				datastore.save(rental.getBike());
				
				venue.modify();
				venue.addBike(rental.getBike());
				datastore.save(venue);
				
				rental.modify();
				rental.setDelivered(true);
				datastore.save(rental);
				
				
				String certificate = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(certificate, Response.Status.OK);
			}else{
				jsonMap.clear();
				jsonMap.put("Error", "User not authorized to return this bike.");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.UNAUTHORIZED);
			}

		}
	}
	
	public static Response addUser(String idRental,Document user) {
		Datastore datastore = BikesDB.getDatastore();

		Rental rental = datastore.get(Rental.class, new ObjectId(idRental));

		if(rental == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Rental not found.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			Client c = datastore.createQuery(Client.class).field("email").equal(user.getString("mail")).get();
			
			if (c == null){
				jsonMap.clear();
				jsonMap.put("Error", "User not found.");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
			}
			
			rental.addAllowedUser(user.getString("mail"));
			datastore.save(rental);
			return ResponseBiker.buildResponse(rental, Response.Status.OK);
		}
	}
}
