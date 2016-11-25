package Resources;

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

import org.bson.types.ObjectId;

import DAO.PenaltyDAO;
import DTO.Penalty;
import Security.Roles;

@Path("/penalty")
public class PenaltyResource {

	//-------------------------------------------------------------------
	//--------------------------------GET--------------------------------
	//-------------------------------------------------------------------

	@GET
	@RolesAllowed(Roles.ADMIN)
	@Path("/{idUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getPenalties(@PathParam("idUser") String user) {
		return PenaltyDAO.getPenalties(user);
	}
	//-------------------------------------------------------------------
	//--------------------------------POST-------------------------------
	//-------------------------------------------------------------------

	@POST
	@RolesAllowed(Roles.ADMIN)
	@Path("/{idUser}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response penalize(@PathParam("isUser") String username) {
		return PenaltyDAO.penalize(username);
	}

	//-------------------------------------------------------------------
	//--------------------------------PUT--------------------------------
	//-------------------------------------------------------------------

	@PUT
	@RolesAllowed(Roles.ADMIN)
	@Path("/{idPenalty}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response payPenalty(@PathParam("idPenalty") ObjectId id) {
		return PenaltyDAO.payPenalty(id);
	}
	//-------------------------------------------------------------------
	//--------------------------------DELET------------------------------
	//-------------------------------------------------------------------
}
