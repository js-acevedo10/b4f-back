package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.mongodb.morphia.Datastore;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import DTO.Donacion;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class DonacionDAO {
	
	public static JsonParser jsonParser = new JsonParser();
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	public static Response getDonaciones() {
		Datastore datastore = BikesDB.getDatastore();
		List<Donacion> donaciones = datastore.createQuery(Donacion.class).asList();
		if (donaciones  == null) {
			jsonMap.clear();
			jsonMap.put("Error", "Error fetching donations");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
		} else {
			return ResponseBiker.buildResponse(donaciones, Response.Status.OK);
		}
	}
	
	public static Response addDonacion(Donacion donacion) {
		Datastore datastore = BikesDB.getDatastore();
		datastore.save(donacion);
		return getDonaciones();
	}
}
