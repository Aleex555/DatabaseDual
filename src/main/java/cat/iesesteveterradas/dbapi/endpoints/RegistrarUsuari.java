package cat.iesesteveterradas.dbapi.endpoints;

import java.util.Random;

import org.json.JSONObject;

import cat.iesesteveterradas.dbapi.persistencia.UsuarisDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuari/registrar")
public class RegistrarUsuari {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response afegirUsuari(String jsonInput,@HeaderParam("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
            ? authorizationHeader.substring(7)
            : null;
            JSONObject input = new JSONObject(jsonInput);
            
            String telefon = input.optString("telefon", null);
            String nickname = input.optString("nickname", null);
            String email = input.optString("email", null);


            // Validación para 'nickname'
            if (nickname == null || nickname.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Nickname requerido\"}").build();
            }

            // Validación para 'telefon'
            if (telefon == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Telèfon requerit\"}").build();
            }

            // Validación para 'email'
            if (email == null || email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Email requerit\"}").build();
            }

            Random random = new Random();
            int codigo1 = random.nextInt(900000) + 100000;
            String codigo = String.valueOf(codigo1);

            UsuarisDao.creaUsuario(nickname,telefon, email,codigo);
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "L'usuari s'ha creat correctament");

            // Crear el objeto JSON para la parte "data"
            JSONObject userData = new JSONObject();
            userData.put("nickname", nickname);
            userData.put("email", email);
            userData.put("codi_validacio", codigo);

            // Añadir el objeto "data" al JSON de respuesta
            jsonResponse.put("data", userData);

            // Retorna la resposta
            String prettyJsonResponse = jsonResponse.toString(4); // 4 espais per indentar
            return Response.ok(prettyJsonResponse).build();
        } catch (Exception e) {
            return Response.serverError().entity("{\"status\":\"ERROR\",\"message\":\"Error en afegir el usuari\"}").build();
        }
    }
}