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

import org.bson.types.ObjectId;

import DAO.BikeDAO;
import DTO.Bike;
import Security.Roles;

@Path("/bikes")
public class BikeResource {

	//-------------------------------------------------------------------
	//--------------------------------GET--------------------------------
	//-------------------------------------------------------------------
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBikes() {
		return BikeDAO.getBikes();
	}
	
	@GET
	@Path("/{bikeId}")
	@RolesAllowed(Roles.ADMIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBikeWithId(@PathParam("bikeId") String bikeId) {
		return BikeDAO.getBikeWithId(bikeId);
	}
	
	@GET
	@Path("/reserve/{bikeId}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserveBike(@PathParam("bikeId") ObjectId bikeId) {
		return BikeDAO.reserveBikeWithId(bikeId);
	}
	
	@Path("/r/{rentalId}")
	@RolesAllowed(Roles.MANAGER)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBikesWithRentId(@PathParam("rentalId") String rentalId) {
		return BikeDAO.getBikesWithRentId(rentalId);
	}

	//-------------------------------------------------------------------
	//--------------------------------POST-------------------------------
	//-------------------------------------------------------------------
	
	@POST
	@RolesAllowed({Roles.ADMIN, Roles.MANAGER})
	@Path("/{idType}/{idVenue}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBike(Bike bike, @PathParam("idType") String idType, @PathParam("idVenue") String idVenue) {
		return BikeDAO.addBike(bike, idType, idVenue);
	}

	//-------------------------------------------------------------------
	//--------------------------------PUT--------------------------------
	//-------------------------------------------------------------------
	
	@PUT
	@Path("/{bikeId}")
	@RolesAllowed(Roles.ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editBike(@PathParam("bikeId") String bikeId,Bike bike)  {
		bike.setId(new ObjectId(bikeId));
		return BikeDAO.editBike(bike);
	}

	//-------------------------------------------------------------------
	//--------------------------------DELETE-----------------------------
	//-------------------------------------------------------------------
	
	@DELETE
	@Path("/{bikeId}")
	@RolesAllowed({Roles.ADMIN, Roles.MANAGER})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBike(@PathParam("bikeId") String bikeId) {
		return BikeDAO.deleteBike(bikeId);
	}
	
}
