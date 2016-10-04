package Resources;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
