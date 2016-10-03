package Resources;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DAO.UserDAO;
import DTO.Admin;
import Security.Roles;

@Path("admin")
public class AdminResource {

public static final String KEY = "ADMIN";
	
	//------------------------------------------------
	//----------------------GET-----------------------
	//------------------------------------------------
	
	@RolesAllowed(Roles.ADMIN)
	@Path("/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAdmin(@PathParam("adminId") String userId) {
		return UserDAO.getAdmin(userId);
	}
	
	@RolesAllowed(Roles.ADMIN)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAdmin() {
		return UserDAO.getAdmin();
	}
	
	//------------------------------------------------
	//----------------------POST----------------------
	//------------------------------------------------
	
	@PermitAll
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAdmin(Admin admin) {
		return UserDAO.addAdmin(admin);
	}
	
	//------------------------------------------------
	//----------------------PUT-----------------------
	//------------------------------------------------
	
	@RolesAllowed(Roles.ADMIN)
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAdmin(Admin admin) {
		return UserDAO.updateAdmin(admin);
	}
	
	//------------------------------------------------
	//----------------------DELETE--------------------
	//------------------------------------------------
	
	
}
