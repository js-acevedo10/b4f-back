package Resources;

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
import DAO.RentPlaceDAO;
import DTO.Bike;
import DTO.RentPlace;
import Security.Roles;

@Path("rentplace")
public class RentPlaceResource {
		//-------------------------------------------------------------------
		//--------------------------------GET--------------------------------
		//-------------------------------------------------------------------
		
		@GET
		@Path("/{placeId}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getPlaceWithId(@PathParam("placeId") String placeId) {
			return RentPlaceDAO.getRentPlaceWithId(placeId);
		}
		//-------------------------------------------------------------------
		//--------------------------------GET--------------------------------
		//-------------------------------------------------------------------
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response getPlaces() {
			return RentPlaceDAO.getRentPlaces();
		}
		//-------------------------------------------------------------------
		//--------------------------------GET--------------------------------
		//-------------------------------------------------------------------
		
		@GET
		@Path("/{placeId}/rentBike/{bikeId}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response rentBike(@PathParam("placeId") String placeId,@PathParam("bikeId") String bikeId) {
			return RentPlaceDAO.rentBike(placeId, bikeId);
		}
		//-------------------------------------------------------------------
		//--------------------------------GET--------------------------------
		//-------------------------------------------------------------------
		
		@GET
		@Path("/{placeId}/returnBike/{bikeId}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response returnBike(@PathParam("placeId") String placeId,@PathParam("bikeId") String bikeId) {
			return RentPlaceDAO.returnBike(placeId, bikeId);
		}

		//-------------------------------------------------------------------
		//--------------------------------POST-------------------------------
		//-------------------------------------------------------------------
		
		@POST
		@RolesAllowed(Roles.ADMIN)
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addPlace(RentPlace place) {
			return RentPlaceDAO.addRentPlace(place);
		}

		//-------------------------------------------------------------------
		//--------------------------------PUT--------------------------------
		//-------------------------------------------------------------------
		
		@PUT
		@Path("/{placeId}")
		@RolesAllowed(Roles.ADMIN)
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response editPlace(@PathParam("placeId") String placeId, RentPlace place) {
			place.setId(new ObjectId(placeId));
			return RentPlaceDAO.editRentPlace(place);
		}

		//-------------------------------------------------------------------
		//--------------------------------DELETE-----------------------------
		//-------------------------------------------------------------------
		
		@DELETE
		@Path("/{placeId}")
		@RolesAllowed(Roles.ADMIN)
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deletePlace(@PathParam("placeId") String placeId) {
			return RentPlaceDAO.deleteRentPlace(placeId);
		}
}
