package cat.iesesteveterradas.dbapi.endpoints;

import org.json.JSONObject;

import cat.iesesteveterradas.dbapi.persistencia.Propietario;
import cat.iesesteveterradas.dbapi.persistencia.PropietarioDao;
import cat.iesesteveterradas.dbapi.persistencia.Usuario;
import cat.iesesteveterradas.dbapi.persistencia.UsuarisDao;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/verificar")
public class CambiarContrasena {
    @POST
    @Path("/usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuari(String jsonInput) {

        try {
            JSONObject input = new JSONObject(jsonInput);

            String email = input.optString("email", null);

            if (email == null || email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"Email requerido\"}").build();
            }

            Propietario propietario = new Propietario();

            JSONObject jsonResponse = new JSONObject();

            propietario = PropietarioDao.encontrarPropietarioPorEmail(email);
            if (propietario == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"No existe este email\"}").build();
            }

            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Gmail correcto, escribe tu nueva contrasena");

            // Retorna la resposta
            String prettyJsonResponse = jsonResponse.toString(4); // 4 espais per indentar
            return Response.ok(prettyJsonResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("{\"status\":\"ERROR\",\"message\":\"Error en hacer el login\"}" + e)
                    .build();
        }
    }

    @POST
    @Path("/contrasena")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiocontrasena(String jsonInput) {

        try {
            JSONObject input = new JSONObject(jsonInput);

            String email = input.optString("email", null);
            String contrasena = input.optString("contrasena", null);

            if (email == null || email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"Email requerido\"}").build();
            }

            if (contrasena == null || contrasena.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"Contrasena requerida\"}").build();
            }

            JSONObject jsonResponse = new JSONObject();

            PropietarioDao.actualizarContrasenaPropietario(email, contrasena);

            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Gmail correcto, escribe tu nueva contrasena");

            // Retorna la resposta
            String prettyJsonResponse = jsonResponse.toString(4); // 4 espais per indentar
            return Response.ok(prettyJsonResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("{\"status\":\"ERROR\",\"message\":\"Error en hacer el login\"}" + e)
                    .build();
        }
    }
}
