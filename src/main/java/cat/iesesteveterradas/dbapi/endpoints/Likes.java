package cat.iesesteveterradas.dbapi.endpoints;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.AlojamientoDao;
import cat.iesesteveterradas.dbapi.persistencia.LikeDao;
import cat.iesesteveterradas.dbapi.persistencia.Reserva;
import cat.iesesteveterradas.dbapi.persistencia.ReservaDao;
import cat.iesesteveterradas.dbapi.persistencia.UsuarisDao;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class Likes {
    private static final Logger logger = LoggerFactory.getLogger(InformacionReserva.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response likes(String jsonInput) {
        try {
            JSONObject input = new JSONObject(jsonInput);
            String alojamientoId = input.optString("alojamientoID");
            String usuarioId = input.optString("usuarioID");

            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"ID de alojamiento requerido\"}").build();
            }
            if (alojamientoId == null || alojamientoId.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"ID de alojamiento requerido\"}").build();
            }

            LikeDao.crearLike(UsuarisDao.encontrarUsuarioPorUserID(usuarioId),
                    AlojamientoDao.encontrarAlojamientoPorId(Integer.parseInt(alojamientoId)));

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Información de las reservas obtenida correctamente.");

            return Response.ok(jsonResponse.toString()).build();
        } catch (Exception e) {
            logger.error("Error al obtener la información de las reservas", e);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "ERROR");
            errorResponse.put("message", "Error al obtener la información de las reservas: " + e.getMessage());
            return Response.serverError().entity(errorResponse.toString()).build();
        }
    }
}