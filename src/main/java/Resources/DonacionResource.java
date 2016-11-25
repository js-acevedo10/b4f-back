package Resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DAO.DonacionDAO;
import DTO.Donacion;

@Path("/donations")
public class DonacionResource {

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDonaciones() {
		return DonacionDAO.getDonaciones();
	}
	
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBike(Donacion donacion) {
		return DonacionDAO.addDonacion(donacion);
	}
}
