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
import DTO.RentPlace;
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

	//-------------------------------------------------------------------
	//--------------------------------POST-------------------------------
	//-------------------------------------------------------------------
	
	@POST
	@Path("/{bikeTypeName}")
	@RolesAllowed(Roles.ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBike(Bike bike, @PathParam("bikeTypeName") String bikeTypeName) {
		return BikeDAO.addBike(bike, bikeTypeName);
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
	@RolesAllowed(Roles.ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBike(@PathParam("bikeId") String bikeId) {
		return BikeDAO.deleteBike(bikeId);
	}
	
}
