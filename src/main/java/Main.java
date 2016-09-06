import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Main class.
 *
 */
public class Main {
	
	public static final String BASE_URI = "http://localhost:8080/";
	
	public static void main(String[] args) throws Exception{
        Server server = startServer();
        server.start();
        server.join();
    }
	
	public static Server startServer() throws Exception {
		//Revisamos si hay puerto en el servidor, es decir, Heroku en nuestro caso
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
        	//Si no, lo corre local
            webPort = "8080";
            System.out.println("El puerto permite TESTS.");
        }

        final Server server = new Server(Integer.valueOf(webPort));
        final WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setParentLoaderPriority(true);

        //Esto lo necesitamos para el directrio ppal
        final String webappDirLocation = "src/main/webapp/";
        //Esto lo necesitamos para que el CORS filter quede registrado
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

        server.setHandler(root);

        return server;
	}
}