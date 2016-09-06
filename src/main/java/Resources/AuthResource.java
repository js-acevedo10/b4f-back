package Resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import DAO.AuthDAO;

@Path("/auth")
public class AuthResource {
	
	//-------------------------------------------------------------------
	//--------------------------------GET--------------------------------
	//-------------------------------------------------------------------

	
	//-------------------------------------------------------------------
	//--------------------------------POST-------------------------------
	//-------------------------------------------------------------------
	
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response login(Document credentials) {
		String username = credentials.getString("username");
		String password = credentials.getString("password");
		return AuthDAO.login(username, password);
	}
	
	//-------------------------------------------------------------------
	//--------------------------------PUT--------------------------------
	//-------------------------------------------------------------------

	//-------------------------------------------------------------------
	//--------------------------------DELET------------------------------
	//-------------------------------------------------------------------
	
}
