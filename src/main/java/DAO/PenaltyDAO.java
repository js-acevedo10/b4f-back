package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import com.google.gson.Gson;

import DTO.Client;
import DTO.Penalty;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class PenaltyDAO {

	public static void main(String[] args) {
//		penalize("juansantiago.acevedocorrea@gmail.com", new Penalty(3500.0, new Date(), new Date(), false));
//		System.out.println(getPenalties("juansantiago.acevedocorrea@gmail.com").getEntity().toString());
//		System.out.println(payPenalty(new ObjectId("57ce1c198ce2cc049aede7ea")).getEntity().toString());
	}

	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();

	public static Response getPenalties(String username) {
		Datastore datastore = BikesDB.getDatastore();

		Client client = datastore.createQuery(Client.class)
				.field("email").equal(username)
				.get(); 
		if (client == null){
			jsonMap.clear();
			jsonMap.put("Error", "User not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		}

		List<Penalty> penalties = datastore.createQuery(Penalty.class)
				.filter("client", client)
				.asList();

		if(penalties == null || penalties.isEmpty()) {
			jsonMap.clear();
			jsonMap.put("Error", "No penalties for this user.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(penalties, Response.Status.OK);
		}
	}

	public static Response penalize(String username, Penalty penalty){
		Datastore datastore = BikesDB.getDatastore();
		if (penalty != null){
			if (penalty.getFee() < 0 || penalty.getSuspensionEnd().before(penalty.getSuspensionStart())){
				jsonMap.clear();
				jsonMap.put("Error", "Fee must be positive or 0. Start date must be before end date.");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.BAD_REQUEST);
			}

			Client client = datastore.createQuery(Client.class)
					.field("email").equal(username)
					.get(); 
			if (client == null){
				jsonMap.clear();
				jsonMap.put("Error", "User not found");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
			}
			
			penalty.setClient(client);
			datastore.save(penalty);

			return ResponseBiker.buildResponse(penalty, Response.Status.OK);
		}

		jsonMap.clear();
		jsonMap.put("Error", "Penalty is null");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Response.Status.NOT_MODIFIED);
	}
	
	public static Response payPenalty(ObjectId id){
		Datastore datastore = BikesDB.getDatastore();
		Penalty penalty = datastore.get(Penalty.class, id);
		
		if (penalty == null){
			jsonMap.clear();
			jsonMap.put("Error", "Penalty not found");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		}
		if (penalty.getPending() != null && !penalty.getPending()){
			jsonMap.clear();
			jsonMap.put("Error", "This penalty has already been payed");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_MODIFIED);
		}
		
		penalty.setFee(0.0);
		penalty.setPending(false);
		
		datastore.save(penalty);
		
		return ResponseBiker.buildResponse(penalty, Response.Status.OK);
	}

}
