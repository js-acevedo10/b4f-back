package Resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DAO.UserDAO;
import DTO.Client;
import Security.Roles;

@RolesAllowed(Roles.CLIENT)
@Path("/client")
public class ClientResource {
	
	public static final String KEY = "CLIENTE";
	
	//------------------------------------------------
	//----------------------GET-----------------------
	//------------------------------------------------
	
	
	
	//------------------------------------------------
	//----------------------POST----------------------
	//------------------------------------------------
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addClient(Client client) {
		return UserDAO.addClient(client);
	}
	
	//------------------------------------------------
	//----------------------PUT-----------------------
	//------------------------------------------------
	
	//------------------------------------------------
	//----------------------DELETE--------------------
	//------------------------------------------------
	
}
