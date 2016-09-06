package Utilities;

import java.util.ArrayList;
import javax.ws.rs.core.Response;

/**
 * Esta clase sirve para enviar respuestas REST sin tener que armarlas siempre,
 * dependiendo de las necesidades <br>
 * vamos a tener o no que separar los métodos y los headers.
 * 
 * @author JuanSantiagoAcev
 */
public class ResponseBiker {

	/**
	 * El asterisco significa que el request puede venir de cualquier url, se
	 * reemplaza por una url en específico
	 */
	public final static String ORIGIN = "*";

	/**
	 * Los headers son las operaciones
	 */
	public final static String HEADERS = "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept";

	/**
	 * Método que construye la respuesta de cualquier método REST
	 * @param respuesta - el objeto que se vaya a enviar (debe ser String, JSON o un objeto anotado con JAX-RS)
	 * @return la respuesta lista para ser enviada
	 */
	public static Response buildResponse(Object respuesta, Response.Status status) {
		respuesta = ObjectIdAdapter.getGson().toJson(respuesta);
		return Response.status(status).entity(respuesta).header("Access-Control-Allow-Headers", HEADERS)
				.header("Access-Control-Allow-Origin", ORIGIN)
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS").build();
	}

	// ------------------------------------------------------------------------------------------------
	// ------------------------UTILIDADES PARA VERIFICACION DE
	// OBJETOS---------------------------------
	// ------------------------------------------------------------------------------------------------

	/**
	 * Método que revisa la nulidad de un objeto
	 * @param o - el objeto
	 * @return false si es nulo, true si no es nulo
	 */
	public static boolean nullCheck(ArrayList<Object> os) {
		for (Object object : os) {
			if (object == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método que revisa un String
	 * @param s - el String
	 * @return false si está vacío, true si no está vacío
	 */
	public static boolean stringCheck(ArrayList<String> s) {
		for (String string : s) {
			if (string.equals("") || string.equals(" ")) {
				return false;
			}
		}
		return true;
	}
}
