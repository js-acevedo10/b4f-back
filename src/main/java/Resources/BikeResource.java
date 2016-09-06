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

import DAO.BikeDao;
import DTO.Bike;
import Security.Roles;

@Path("bikes")
public class BikeResource {

	//-------------------------------------------------------------------
	//--------------------------------GET--------------------------------
	//-------------------------------------------------------------------
	
	@GET
	@Path("/{bikeId}")
	@RolesAllowed(Roles.ADMIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBikeWithId(@PathParam("bikeId") String bikeId) {
		return BikeDao.getBikeWithId(bikeId);
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
		return BikeDao.addBike(bike, bikeTypeName);
	}

	//-------------------------------------------------------------------
	//--------------------------------PUT--------------------------------
	//-------------------------------------------------------------------
	
	@PUT
	@RolesAllowed(Roles.ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editBike(Bike bike) {
		return BikeDao.editBike(bike);
	}

	//-------------------------------------------------------------------
	//--------------------------------DELETE-----------------------------
	//-------------------------------------------------------------------
	
	@PUT
	@Path("/{bikeId}")
	@RolesAllowed(Roles.ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBike(@PathParam("bikeId") String bikeId) {
		return BikeDao.deleteBike(bikeId);
	}
	
}
