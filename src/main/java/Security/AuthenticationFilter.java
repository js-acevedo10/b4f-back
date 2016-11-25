package Security;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
 
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
 
import org.glassfish.jersey.internal.util.Base64;

import DAO.AuthDAO;

@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;
     
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
      
    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        Method method = resourceInfo.getResourceMethod();
        //Reviso si el acceso al recurso es público
        if( !method.isAnnotationPresent(PermitAll.class)) {
            //Reviso si el acceso al recurso es privado
            if(method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"Access blocked for all users !!\"}").build());
            }
              
            //Pido los headers del request dentro de un map
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
              
            //Pido, del map, el header de autorizacion para verificar autenticación
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
              
            //Si no tiene autorización en el header, bloqueo el recurso
            if(authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"You cannot access this resource with your role\"}").build());
            }
              
            //Pido el usuairo y la contraseña encriptados
            if(authorization != null && authorization.get(0) != null) {
            	final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
            	//Desencripto usuario y contraseña
                String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;
      
                //Split username and password tokens
                final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
                final String username = tokenizer.nextToken();
                final String password = tokenizer.nextToken();
                  
                //Verifico si el rol coincide con las restricciones del recurso
                if(method.isAnnotationPresent(RolesAllowed.class)) {
                	//Aqui pedimos los roles permitidos del método como tal
                    RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                    Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                      
                    //Aquí se hace la validación del rol
                    if(!isUserAllowed(username, password, rolesSet)) {
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                                .entity("{\"error\":\"You cannot access this resource with your role\"}").build());
                    }
                }
            } else {
            	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"You cannot access this resource with your role\"}").build());
            }
        }
    }
    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
    	String userRole = AuthDAO.getUserRole(username, password);
        return rolesSet.contains(userRole);
    }
}
