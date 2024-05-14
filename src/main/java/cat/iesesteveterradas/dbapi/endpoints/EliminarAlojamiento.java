package cat.iesesteveterradas.dbapi.endpoints;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.Alojamiento;
import cat.iesesteveterradas.dbapi.persistencia.AlojamientoDao;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/eliminar/alojamiento")
public class EliminarAlojamiento {
    private static final Logger logger = LoggerFactory.getLogger(PedirUnAlojamiento.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarAlojamiento(String jsonInput) {

        try {
            JSONObject input = new JSONObject(jsonInput);
            String id = input.optString("id", null);

            if (id == null || id.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"Email requerido\"}").build();
            }

            AlojamientoDao.eliminarAlojamientoPorId(Integer.parseInt(id));

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Alojamiento eliminado correctamente.");

            return Response.ok(jsonResponse.toString(4)).build(); // 4 espacios para indentar
        } catch (Exception e) {
            logger.error("Error al obtener la información de los alojamientos", e);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "ERROR");
            errorResponse.put("message", "Error al obtener la información de los alojamientos: " + e.getMessage());
            return Response.serverError().entity(errorResponse.toString()).build();
        }
    }
}
