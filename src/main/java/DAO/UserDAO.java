package DAO;

import javax.ws.rs.core.Response;

import DTO.Client;
import Utilities.BikesDB;
import Utilities.ResponseBiker;

public class UserDAO {
	
	public static Response addClient(Client client) {
		BikesDB.getDatastore().save(client);
		return ResponseBiker.buildResponse(client, Response.Status.OK);
	}
}
