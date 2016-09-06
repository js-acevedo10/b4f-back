package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import DTO.Admin;
import DTO.Client;
import DTO.Manager;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class UserDAO {
	
	public static Map<String, String> jsonMap = new HashMap<String, String>();
	public static Gson g = new Gson();
	
	//------------------------------------------------------------------------
	//---------------------------------CLIENTE--------------------------------
	//------------------------------------------------------------------------
	
	public static Response getClients() {
		List<Client> clients = BikesDB.getDatastore().createQuery(Client.class).asList();
		if(clients != null && !clients.isEmpty()) {
			return ResponseBiker.buildResponse(clients, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "Users not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}
	
	public static Response getClient(String idClient) {
		Client client = BikesDB.getDatastore().get(Client.class, idClient);
		if(client != null) {
			return ResponseBiker.buildResponse(client, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "User not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}
	
	public static Response addClient(Client client) {
		client.points = 0;
		client.suspended = false;
		BikesDB.getDatastore().save(client);
		return AuthDAO.login(client.email, client.password);
	}
	
	public static Response updateClient(Client client) {
		BikesDB.getDatastore().save(client);
		return ResponseBiker.buildResponse(client, Status.OK);
	}

	//------------------------------------------------------------------------
	//---------------------------------ADMIN----------------------------------
	//------------------------------------------------------------------------

	public static Response getAdmin(String idAdmin) {
		Admin admin = BikesDB.getDatastore().get(Admin.class, idAdmin);
		if(admin != null) {
			return ResponseBiker.buildResponse(admin, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "User not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}
	
	public static Response addAdmin(Admin admin) {
		BikesDB.getDatastore().save(admin);
		return AuthDAO.login(admin.email, admin.password);
	}
	
	public static Response updateAdmin(Admin admin) {
		BikesDB.getDatastore().save(admin);
		return ResponseBiker.buildResponse(admin, Status.OK);
	}
	
	//------------------------------------------------------------------------
	//---------------------------------MANAGER--------------------------------
	//------------------------------------------------------------------------
	
	public static Response getManager() {
		List<Manager> managers = BikesDB.getDatastore().createQuery(Manager.class).asList();
		if(managers != null && !managers.isEmpty()) {
			return ResponseBiker.buildResponse(managers, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "Users not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}
	
	public static Response getManager(String idManager) {
		Manager manager = BikesDB.getDatastore().get(Manager.class, idManager);
		if(manager != null) {
			return ResponseBiker.buildResponse(manager, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "User not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}
	
	public static Response addManager(Manager manager) {
		BikesDB.getDatastore().save(manager);
		return AuthDAO.login(manager.email, manager.password);
	}
	
	public static Response updateManager(Manager manager) {
		BikesDB.getDatastore().save(manager);
		return ResponseBiker.buildResponse(manager, Status.OK);
	}
}
