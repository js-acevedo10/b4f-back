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
import DTO.Manager;
import Security.Roles;

@Path("manager")
public class ManagerResource {

	//------------------------------------------------
	//----------------------GET-----------------------
	//------------------------------------------------

	@RolesAllowed({Roles.ADMIN, Roles.MANAGER})
	@Path("/{userId}")
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getManager(@PathParam("managerId") String userId) {
		return UserDAO.getManager(userId);
	}

	//------------------------------------------------
	//----------------------POST----------------------
	//------------------------------------------------

	@PermitAll
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addManager(Manager manager) {
		return UserDAO.addManager(manager);
	}

	//------------------------------------------------
	//----------------------PUT-----------------------
	//------------------------------------------------

	@RolesAllowed({Roles.ADMIN, Roles.MANAGER})
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateManager(Manager manager) {
		return UserDAO.updateManager(manager);
	}

	//------------------------------------------------
	//----------------------DELETE--------------------
	//------------------------------------------------
}
