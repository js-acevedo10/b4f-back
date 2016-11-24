package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.Document;
import org.bson.types.ObjectId;

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
		Client client = BikesDB.getDatastore().get(Client.class, new ObjectId(idClient));
		if(client != null) {
			return ResponseBiker.buildResponse(client, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "User not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}

	public static Response getClient(Document mail) {
		Client client = BikesDB.getDatastore().createQuery(Client.class).field("email").equal(mail.getString("mail")).get();
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
		if(BikesDB.getDatastore().save(client) == null) {
			AuthDAO.login("", "");
		}
		return AuthDAO.login(client.email, client.password);
	}

	public static Response updateClient(Client client) {
		BikesDB.getDatastore().save(client);
		return ResponseBiker.buildResponse(client, Status.OK);
	}

	public static Response removeClient(String idClient) {
		Client client = BikesDB.getDatastore().get(Client.class, new ObjectId(idClient));
		if(client != null) {
			client.delete();
			BikesDB.getDatastore().save(client);
			jsonMap.clear();
			jsonMap.put("Succes", "User removed.");
			String callback = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(callback, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "User not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}

	//------------------------------------------------------------------------
	//---------------------------------ADMIN----------------------------------
	//------------------------------------------------------------------------

	public static Response getAdmin(String idAdmin) {
		Admin admin = BikesDB.getDatastore().get(Admin.class, new ObjectId(idAdmin));
		if(admin != null) {
			return ResponseBiker.buildResponse(admin, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "User not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}

	public static Response getAdmin() {
		List<Admin> managers = BikesDB.getDatastore().createQuery(Admin.class).asList();
		if(managers != null && !managers.isEmpty()) {
			return ResponseBiker.buildResponse(managers, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "Users not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}

	public static Response addAdmin(Admin admin) {
		if(BikesDB.getDatastore().save(admin) == null) {
			AuthDAO.login("", "");
		}
		return AuthDAO.login(admin.email, admin.password);
	}

	public static Response updateAdmin(Admin admin) {
		admin.modify();
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
		Manager manager = BikesDB.getDatastore().get(Manager.class, new ObjectId(idManager));
		if(manager != null) {
			return ResponseBiker.buildResponse(manager, Status.OK);
		}
		jsonMap.clear();
		jsonMap.put("Error", "User not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}

	public static Response addManager(Manager manager) {
		if(BikesDB.getDatastore().save(manager) == null) {
			AuthDAO.login("", "");
		}
		return AuthDAO.login(manager.email, manager.password);
	}

	public static Response updateManager(Manager manager) {
		manager.modify();
		BikesDB.getDatastore().save(manager);
		return ResponseBiker.buildResponse(manager, Status.OK);
	}

	public static Response removeManager(String idManager) {
		Manager manager = BikesDB.getDatastore().get(Manager.class, new ObjectId(idManager));
		if(manager != null) {
			manager.delete();
			BikesDB.getDatastore().save(manager);
			jsonMap.clear();
			jsonMap.put("Succes", "User removed.");
			String callback = g.toJson(jsonMap);
			return ResponseBiker.buildResponse(callback, Status.OK);

		}
		jsonMap.clear();
		jsonMap.put("Error", "User not found.");
		String error = g.toJson(jsonMap);
		return ResponseBiker.buildResponse(error, Status.NOT_FOUND);
	}
}
