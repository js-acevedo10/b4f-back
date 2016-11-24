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
import Security.Roles;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class AuthDAO {
	
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
					resp.put(Base64.encodeAsString("id"), Base64.encodeAsString(admin.getId().toHexString()));
					resp.put(Base64.encodeAsString("name"), Base64.encodeAsString(admin.getName()));
					resp.put(Base64.encodeAsString("role"), Base64.encodeAsString("admin"));
					resp.put(Base64.encodeAsString("email"), Base64.encodeAsString(admin.getEmail()));
					resp.put(Base64.encodeAsString("token"), Base64.encodeAsString(token));
					
					return ResponseBiker.buildResponse(resp, Response.Status.OK);
				}
			} else {
				String token = "Basic " + Base64.encodeAsString(manager.getEmail() + ":" + manager.getPassword());
				Document resp = new Document();
				resp.put(Base64.encodeAsString("id"), Base64.encodeAsString(manager.getId().toHexString()));
				resp.put(Base64.encodeAsString("name"), Base64.encodeAsString(manager.getName()));
				resp.put(Base64.encodeAsString("role"), Base64.encodeAsString("manager"));
				resp.put(Base64.encodeAsString("email"), Base64.encodeAsString(manager.getEmail()));
				resp.put(Base64.encodeAsString("token"), Base64.encodeAsString(token));
				return ResponseBiker.buildResponse(resp, Response.Status.OK);
			}
		} else {
			String token = "Basic " + Base64.encodeAsString(client.getEmail() + ":" + client.getPassword());
			Document resp = new Document();
			resp.put(Base64.encodeAsString("id"), Base64.encodeAsString(client.getId().toHexString()));
			resp.put(Base64.encodeAsString("name"), Base64.encodeAsString(client.getName()));
			resp.put(Base64.encodeAsString("role"), Base64.encodeAsString("client"));
			resp.put(Base64.encodeAsString("email"), Base64.encodeAsString(client.getEmail()));
			resp.put(Base64.encodeAsString("token"), Base64.encodeAsString(token));
			resp.put(Base64.encodeAsString("points"), Base64.encodeAsString(client.getPoints()+""));
			resp.put(Base64.encodeAsString("suspended"), Base64.encodeAsString(client.isSuspended()+""));
			if(client.isSuspended()){
				jsonMap.clear();
				jsonMap.put("Error", "User is suspended.");
				String error = g.toJson(jsonMap);
				return ResponseBiker.buildResponse(error, Response.Status.CONFLICT);
			}else{
				return ResponseBiker.buildResponse(resp, Response.Status.OK);
			}
		}
	}
	
	public static String getUserRole(String username, String password) {
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
					return "unknown";
				} else {
					return Roles.ADMIN;
				}
			} else {
				return Roles.MANAGER;
			}
		} else {
			return Roles.CLIENT;
		}
	}
}