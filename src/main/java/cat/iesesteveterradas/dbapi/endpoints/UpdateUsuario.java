package cat.iesesteveterradas.dbapi.endpoints;

import java.util.Random;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.Propietario;
import cat.iesesteveterradas.dbapi.persistencia.PropietarioDao;
import cat.iesesteveterradas.dbapi.persistencia.Usuario;
import cat.iesesteveterradas.dbapi.persistencia.UsuarisDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuari/login")
public class UpdateUsuario {
    private static final Logger logger = LoggerFactory.getLogger(UsuarisDao.class);
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuario(String jsonInput) {
        
        try {
            JSONObject input = new JSONObject(jsonInput);
            
            String email = input.optString("email", null);
            String contrasena = input.optString("contraseña", null);
            String telefono = input.optString("telefono", null);

            // Validación para 'email'
            if (email == null || email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Email requerido\"}").build();
            }

            if (contrasena == null || contrasena.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Contraseña requerida \"}").build();
            }

            if (telefono == null || telefono.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Telefono requerida \"}").build();
            }

            




            JSONObject jsonResponse = new JSONObject();

            JSONObject userData = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Credenciales correctas");
            

            // Añadir el objeto "data" al JSON de respuesta
            jsonResponse.put("data", userData);

            // Retorna la resposta
            String prettyJsonResponse = jsonResponse.toString(4); // 4 espais per indentar
            return Response.ok(prettyJsonResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("{\"status\":\"ERROR\",\"message\":\"Error en hacer el login\"}"+e).build();
        }
    }
}
