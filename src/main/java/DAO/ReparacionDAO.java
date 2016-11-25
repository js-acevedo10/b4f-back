package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import DTO.Bike;
import DTO.Reparacion;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class ReparacionDAO {
	
	public static JsonParser jsonParser = new JsonParser();
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	//------------------------------------------------------------------------
		//---------------------------------REPARACION--------------------------------
		//------------------------------------------------------------------------

		public static Response getReparaciones() {
			List<Reparacion> reparaciones = BikesDB.getDatastore().createQuery(Reparacion.class).asList();
			if(reparaciones != null && !reparaciones.isEmpty()) {
				return ResponseBiker.buildResponse(reparaciones, Status.OK);
			}
			jsonMap.clear();
			jsonMap.put("Error", "Reparaciones not found.");
			String error = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
		}
		
		public static Response getReparacionWithId(String reparacionId) {
			Datastore datastore = BikesDB.getDatastore();
			final Query<Reparacion> queryReparacion = datastore.createQuery(Reparacion.class);
			queryReparacion.field("id").equal(new ObjectId(reparacionId));
			Reparacion reparacion = queryReparacion.get();
			if (reparacion  == null) {
				jsonMap.clear();
				jsonMap.put("Error", "Reparacion not found");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
			} else {
				return ResponseBiker.buildResponse(reparacion, Response.Status.OK);
			}
		}
		
		
		public static Response addReparacion(Reparacion reparacion) {
			BikesDB.getDatastore().save(reparacion);
			return ResponseBiker.buildResponse(reparacion, Response.Status.OK);
		}
		
		public static Response editReparacion(Reparacion reparacion) {
			Datastore datastore = BikesDB.getDatastore();
			Reparacion resultReparacion = datastore.get(Reparacion.class, reparacion.getId());
			if (resultReparacion == null) {
				jsonMap.clear();
				jsonMap.put("Error", "Reparacion not found");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
			} else {
				reparacion.setId(resultReparacion.getId());
				reparacion.setFixName(resultReparacion.getFixName());
				reparacion.setFee(resultReparacion.getFee());
				datastore.save(reparacion);
				return ResponseBiker.buildResponse(reparacion, Response.Status.OK);
			}
			
			
		}
		
		public static Response deleteReparacion(String reparacionId) {
			Datastore datastore = BikesDB.getDatastore();
			final Query<Reparacion> reparacionQuery = datastore.createQuery(Reparacion.class);
			reparacionQuery.field("id").equal(new ObjectId(reparacionId));
			Reparacion resultReparacion = reparacionQuery.get();
			if (resultReparacion == null) {
				jsonMap.clear();
				jsonMap.put("Error", "Rent Place not found");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
			} else {
				resultReparacion.delete();
				datastore.save(resultReparacion);
				return ResponseBiker.buildResponse("Reparacion Deleted", Response.Status.OK);
			}
			
		}

}
