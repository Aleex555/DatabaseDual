package cat.iesesteveterradas.dbapi.endpoints;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.Alojamiento;
import cat.iesesteveterradas.dbapi.persistencia.AlojaminetoDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/informacion")
public class Informacion {
    private static final Logger logger = LoggerFactory.getLogger(Informacion.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response informacionAndroid(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size) {
        
        try {
            List<Alojamiento> alojamientos = AlojaminetoDao.encontrarAlojamientosPaginados(page, size);
            

            JSONArray alojamientosJsonArray = new JSONArray();
            for (Alojamiento alojamiento : alojamientos) {
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
                    alojamientoJson.put("nombrePropietario", alojamiento.getPropietario().getNombre()); // Asegúrate de que getNombre() existe en Propietario
                } else {
                    alojamientoJson.put("nombrePropietario", "No disponible");
                }

                alojamientosJsonArray.put(alojamientoJson);
            }

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Datos de alojamientos obtenidos correctamente.");
            jsonResponse.put("data", alojamientosJsonArray);

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
