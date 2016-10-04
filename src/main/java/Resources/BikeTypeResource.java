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

import DAO.BikeTypeDAO;
import DTO.BikeType;
import Security.Roles;

@Path("bikeTypes")
public class BikeTypeResource {

	//-------------------------------------------------------------------
	//--------------------------------GET--------------------------------
	//-------------------------------------------------------------------
	
	@GET
	@RolesAllowed({Roles.ADMIN, Roles.MANAGER, Roles.CLIENT})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBikeTypes() {
		return BikeTypeDAO.getBikeTypes();
	}
	
	@GET
	@Path("/{bikeTypeName}")
	@RolesAllowed(Roles.ADMIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBikeTypeWithId(@PathParam("bikeTypeName") String bikeTypeName) {
		return BikeTypeDAO.getBikeTypeWithName(bikeTypeName);
	}

	//-------------------------------------------------------------------
	//--------------------------------POST-------------------------------
	//-------------------------------------------------------------------
	
	@POST
	@RolesAllowed({Roles.ADMIN, Roles.MANAGER})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBikeType(BikeType bikeType) {
		return BikeTypeDAO.addBikeType(bikeType);
	}

	//-------------------------------------------------------------------
	//--------------------------------PUT--------------------------------
	//-------------------------------------------------------------------
	
	@PUT
	@RolesAllowed(Roles.ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editBikeType(BikeType bikeType) {
		return BikeTypeDAO.editBikeType(bikeType);
	}

	//-------------------------------------------------------------------
	//--------------------------------DELETE-----------------------------
	//-------------------------------------------------------------------
	
	@PUT
	@Path("/{bikeTypeId}")
	@RolesAllowed(Roles.ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBikeType(@PathParam("bikeTypeId") String bikeTypeId) {
		return BikeTypeDAO.deleteBikeType(bikeTypeId);
	}
	
}
