import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import Security.AuthenticationFilter;
import Security.GsonMessageBodyHandler;

@SuppressWarnings("deprecation")
public class MainApplication extends ResourceConfig  {

	public MainApplication() {
		packages("");
        register(GsonMessageBodyHandler.class);
        register(LoggingFilter.class);
        //Aquí decimos que es necesario pasar por el filtro de autenticación antes de que la aplicación responda cualquier petición
        register(AuthenticationFilter.class);
	}
	
}