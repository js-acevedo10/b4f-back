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

import org.bson.Document;

import DAO.RentalDAO;
import Security.Roles;

@Path("/rental")
public class RentalResource {

	//-------------------------------------------------------------------
	//--------------------------------GET--------------------------------
	//-------------------------------------------------------------------

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getRentals() {
		return RentalDAO.getRentals();
	}
	
	@GET
	@Path("/{clientId}")
	@RolesAllowed(Roles.CLIENT)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getRentals(@PathParam("clientId") String client) {
		return RentalDAO.getRentals(client);
	}

	//-------------------------------------------------------------------
	//--------------------------------PUT--------------------------------
	//-------------------------------------------------------------------

	@PUT
	@PermitAll
	@Path("/{idRental}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addUserToRental(@PathParam("idRental") String rentalId, Document user) {
		return RentalDAO.addUser(rentalId, user);
	}

	//-------------------------------------------------------------------
	//--------------------------------POST--------------------------------
	//-------------------------------------------------------------------

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response returnBike(Document returnInfo) {
		return RentalDAO.returnBike(returnInfo);
	}
}
