package Resources;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import DAO.UserDAO;
import DTO.Client;
import Security.Roles;

@Path("/client")
public class ClientResource {
	
	public static final String KEY = "CLIENTE";
	
	//------------------------------------------------
	//----------------------GET-----------------------
	//------------------------------------------------
	
	@RolesAllowed({Roles.ADMIN, Roles.MANAGER})
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClients() {
		return UserDAO.getClients();
	}
	
	@Path("/{userId}")
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClient(@PathParam("userId") String userId) {
		return UserDAO.getClient(userId);
	}
	
	@Path("/m")
	@PermitAll
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClient(Document mail) {
		return UserDAO.getClient(mail);
	}
	
	//------------------------------------------------
	//----------------------POST----------------------
	//------------------------------------------------
	
	@PermitAll
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addClient(Client client) {
		return UserDAO.addClient(client);
	}
	
	//------------------------------------------------
	//----------------------PUT-----------------------
	//------------------------------------------------
	
	@RolesAllowed({Roles.ADMIN, Roles.CLIENT})
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateClient(Client client) {
		return UserDAO.updateClient(client);
	}
	
	//------------------------------------------------
	//----------------------DELETE--------------------
	//------------------------------------------------
	
	@Path("/{userId}")
	@RolesAllowed({Roles.ADMIN})
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeClient(@PathParam("userId") String userId) {
		return UserDAO.removeClient(userId);
	}
}
