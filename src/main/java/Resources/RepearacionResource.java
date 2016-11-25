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


import DAO.ReparacionDAO;
import DTO.Reparacion;
import Security.Roles;

@Path("reparacion")
public class RepearacionResource {
	
	        //-------------------------------------------------------------------
			//--------------------------------GET--------------------------------
			//-------------------------------------------------------------------
			
			@GET
			@Produces(MediaType.APPLICATION_JSON)
			public Response getReparaciones() {
				return ReparacionDAO.getReparaciones();
			}
			
			//-------------------------------------------------------------------
			//--------------------------------PUT--------------------------------
			//-------------------------------------------------------------------
			
			@PUT
			@Path("/{reparacionId}")
			@RolesAllowed(Roles.ADMIN)
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			public Response editPlace(@PathParam("reparacionId") String reparacionId, Reparacion reparacion) {
				reparacion.setId(new ObjectId(reparacionId));
				return ReparacionDAO.editReparacion(reparacion);
			}
			
			//-------------------------------------------------------------------
			//--------------------------------POST-------------------------------
			//-------------------------------------------------------------------
			
			@POST
			@RolesAllowed(Roles.ADMIN)
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			public Response addReparacion(Reparacion reparacion) {
				return ReparacionDAO.addReparacion(reparacion);
			}
			
			
			//-------------------------------------------------------------------
			//--------------------------------DELETE-----------------------------
			//-------------------------------------------------------------------
			
			@DELETE
			@Path("/{reparacionId}")
			@RolesAllowed(Roles.ADMIN)
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			public Response deletePlace(@PathParam("reparacionId") String reparacionId) {
				return ReparacionDAO.deleteReparacion(reparacionId);
			}

}
