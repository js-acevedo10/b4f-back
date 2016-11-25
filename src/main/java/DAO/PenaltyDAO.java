package DAO;

import java.util.Calendar;
import java.util.Date;
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
				.filter("client", client).field("deleted").equal(false)
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

	public static Response penalize(String username){
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
		Double points = client.getPoints();
		client.setPoints(0);
		Penalty penalty = null;
		
		if (points > 50){
			//cada punto equivale a 100 pesos que pueden ser redimidos como bonos
			double prev = client.getBonus();
			client.setBonus(prev+(points*100));
			
			datastore.save(client);
			
			jsonMap.clear();
			jsonMap.put("Success", "User bonus loaded to account");
			String response = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(response, Response.Status.OK);
		}
		else if (points < 0){
			penalty = new Penalty();
			penalty.setPending(true);
			penalty.setSuspensionStart(new Date());

			if (points > -100){
				penalty.setFee(0.0);
				penalty.setSuspensionEnd(new Date());
				penalty.setTrainingRequiered(true);
				//asistencia obligatorio a capacitación pedagógica
			}
			else if (points > -200){
				//cada punto negativo corresponde a 500 pesos de multa
				penalty.setFee((-1)*points*500);
				penalty.setSuspensionEnd(new Date());
				penalty.setTrainingRequiered(false);
			}
			else{
				//cada punto negativo corresponde a 7000 pesos por punto y suspensión del
//				servicio por 3 años
				penalty.setFee((-1)*points*7000);
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.YEAR, 3);
				penalty.setSuspensionEnd(c.getTime());
				penalty.setTrainingRequiered(false);
				client.setSuspended(true);
			}
		}
		
		if (penalty != null){
			if (penalty.getFee() < 0 || penalty.getSuspensionEnd().before(penalty.getSuspensionStart())){
				jsonMap.clear();
				jsonMap.put("Error", "Fee must be positive or 0. Start date must be before end date.");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.BAD_REQUEST);
			}
			
			datastore.save(client);
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
