package cat.iesesteveterradas.dbapi.endpoints;

import java.util.Random;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.PropietarioDao;
import cat.iesesteveterradas.dbapi.persistencia.UsuarisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuari/registrar")
public class RegistrarUsuari {
    private static final Logger logger = LoggerFactory.getLogger(RegistrarUsuari.class);
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response afegirUsuari(String jsonInput) {
        try {
            JSONObject input = new JSONObject(jsonInput);
            String telefono = input.optString("telefono", null);
            String nombre = input.optString("nombre", null);
            String email = input.optString("email", null);
            String contrasena = input.optString("contraseña", null);
            String tipo = input.optString("tipo", null);
            


            // Validación para 'nombre'
            if (nombre == null || nombre.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Nombre requerido\"}").build();
            }

            // Validación para 'telefon'
            if (telefono == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Telefono requerido\"}").build();
            }

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

            
            //System.out.println(tipo);

            if(tipo.equals("propietario") ){
                PropietarioDao.creaPropietario(nombre, email, telefono, contrasena);
            }else{
                UsuarisDao.creaUsuario(nombre,email, telefono,contrasena);
            }
            
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Usuario creado correctamente");

            // Crear el objeto JSON para la parte "data"
            JSONObject userData = new JSONObject();
            userData.put("nombre", nombre);
            userData.put("email", email);

            // Añadir el objeto "data" al JSON de respuesta
            jsonResponse.put("data", userData);

            // Retorna la resposta
            String prettyJsonResponse = jsonResponse.toString(4); // 4 espais per indentar
            return Response.ok(prettyJsonResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("{\"status\":\"ERROR\",\"message\":\"Error en afegir el usuari\"}"+e).build();
        }
    }
}