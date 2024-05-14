package cat.iesesteveterradas.dbapi.endpoints;

import cat.iesesteveterradas.dbapi.persistencia.AlojamientoDao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.Alojamiento;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pedir/alojamiento")
public class PedirUnAlojamiento {
    private static final Logger logger = LoggerFactory.getLogger(PedirUnAlojamiento.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response informacionFlutter(String jsonInput) {

        try {
            JSONObject input = new JSONObject(jsonInput);
            String id = input.optString("id", null);

            if (id == null || id.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"Id requerido\"}").build();
            }

            Alojamiento alojamiento = AlojamientoDao.encontrarAlojamientoPorId(Integer.parseInt(id));

            JSONObject alojamientoJson = new JSONObject();
            alojamientoJson.put("nombre", alojamiento.getNombre());
            alojamientoJson.put("descripcion", alojamiento.getDescripcion());
            alojamientoJson.put("direccion", alojamiento.getDireccion());
            alojamientoJson.put("capacidad", alojamiento.getCapacidad());
            alojamientoJson.put("reglas", alojamiento.getReglas());
            alojamientoJson.put("precioPorNoche", alojamiento.getPrecioPorNoche());
            alojamientoJson.put("urlFoto", alojamiento.getUrlFoto());
            alojamientoJson.put("alojamientoID", alojamiento.getAlojamientoID());
            if (alojamiento.getPropietario() != null) {
                alojamientoJson.put("nombrePropietario", alojamiento.getPropietario().getNombre());
            } else {
                alojamientoJson.put("nombrePropietario", "No disponible");
            }

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Datos de alojamientos obtenidos correctamente.");
            jsonResponse.put("data", alojamientoJson);

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
