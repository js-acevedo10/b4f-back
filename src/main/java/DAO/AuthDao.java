package DAO;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.bson.Document;
import org.glassfish.jersey.internal.util.Base64;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import DTO.Admin;
import DTO.Client;
import DTO.Manager;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class AuthDao {
	
	public static void main(String[] args) {
		System.out.println(login("juansantiago.acevedocorrea@gmail.com", "1234567").getEntity().toString());
	}
	
	public static JsonParser jsonParser = new JsonParser();
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	public static Response login(String username, String password) {
		Datastore datastore = BikesDB.getDatastore();
		final Query<Client> queryClient = datastore.createQuery(Client.class);
		queryClient.and(
			queryClient.criteria("email").equal(username),
			queryClient.criteria("password").equal(password)
		);
		Client client = queryClient.get();
		if(client == null) {
			final Query<Manager> queryManager = datastore.createQuery(Manager.class);
			queryManager.and(
				queryManager.criteria("email").equal(username),
				queryManager.criteria("password").equal(password)
			);
			Manager manager = queryManager.get();
			if(manager == null) {
				final Query<Admin> queryAdmin = datastore.createQuery(Admin.class);
				queryAdmin.and(
					queryAdmin.criteria("email").equal(username),
					queryAdmin.criteria("password").equal(password)
				);
				Admin admin = queryAdmin.get();
				if(admin == null) {
					jsonMap.clear();
					jsonMap.put("Error", "User not found.");
					String error = g.toJson(jsonMap);
					return ResponseBiker.buildResponse(error, Response.Status.NOT_FOUND);
				} else {
					String token = "Basic " + Base64.encodeAsString(admin.getEmail() + ":" + admin.getPassword());
					Document resp = new Document();
					resp.put("id", admin.getId().toHexString());
					resp.put("name", admin.getName());
					resp.put("role", "admin");
					resp.put("email", admin.getEmail());
					resp.put("token", token);
					
					return ResponseBiker.buildResponse(resp, Response.Status.OK);
				}
			} else {
				String token = "Basic " + Base64.encodeAsString(manager.getEmail() + ":" + manager.getPassword());
				Document resp = new Document();
				resp.put("id", manager.getId().toHexString());
				resp.put("name", manager.getName());
				resp.put("role", "manager");
				resp.put("email", manager.getEmail());
				resp.put("token", token);
				return ResponseBiker.buildResponse(resp, Response.Status.OK);
			}
		} else {
			String token = "Basic " + Base64.encodeAsString(client.getEmail() + ":" + client.getPassword());
			Document resp = new Document();
			resp.put("id", client.getId().toHexString());
			resp.put("name", client.getName());
			resp.put("role", "client");
			resp.put("email", client.getEmail());
			resp.put("token", token);
			resp.put("points", client.getPoints());
			resp.put("suspended", client.isSuspended());
			return ResponseBiker.buildResponse(resp, Response.Status.OK);
		}
	}
}