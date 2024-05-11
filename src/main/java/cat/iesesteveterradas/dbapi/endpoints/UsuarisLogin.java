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
public class UsuarisLogin {
    private static final Logger logger = LoggerFactory.getLogger(UsuarisDao.class);
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuari(String jsonInput) {
        
        try {
            JSONObject input = new JSONObject(jsonInput);
            
            String email = input.optString("email", null);
            String contrasena = input.optString("contraseña", null);
            String tipo = input.optString("tipo", null);

            // Validación para 'email'
            if (email == null || email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Email requerido\"}").build();
            }

            if (contrasena == null || contrasena.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Contraseña requerida \"}").build();
            }

            if (tipo == null || tipo.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Contraseña requerida \"}").build();
            }

            Propietario propietario = new Propietario();
            Usuario user = new Usuario();

            JSONObject jsonResponse = new JSONObject();

            JSONObject userData = new JSONObject();

            if(tipo.equals("propietario") ){
                propietario = PropietarioDao.encontrarPropietarioPorEmailYContrasena(email, contrasena);
                if (propietario == null){
                    return Response.status(Response.Status.ACCEPTED).entity("{\"status\":\"ERROR\",\"message\":\"Propietario no encontrado\"}").build();
                }else{
                    userData.put("id", propietario.getPropietarioID());
                }
            }else{
                user  = UsuarisDao.encontrarUsuarioPorEmailYContrasena(email, contrasena);
                if (user == null){
                    return Response.status(Response.Status.ACCEPTED).entity("{\"status\":\"ERROR\",\"message\":\"Usuario no encontrado\"}").build();
                }else{
                    userData.put("id", user.getUserID());
                    userData.put("gmail",user.getEmail());
                    userData.put("telefono",user.getTelefono());
                    userData.put("nombre",user.getNombre());
                    userData.put("url",user.geturlFotoPerfil());
                }
            }

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