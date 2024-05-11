package cat.iesesteveterradas.dbapi.endpoints;

import cat.iesesteveterradas.dbapi.persistencia.Reserva;
import cat.iesesteveterradas.dbapi.persistencia.Alojamiento;
import cat.iesesteveterradas.dbapi.persistencia.AlojamientoDao;
import cat.iesesteveterradas.dbapi.persistencia.ReservaDao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/reservas")
public class InformacionReserva {
    private static final Logger logger = LoggerFactory.getLogger(InformacionReserva.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasPorAlojamiento(String jsonInput) {
        try {
            JSONObject input = new JSONObject(jsonInput);
            String alojamientoId = input.optString("alojamientoID");

            if (alojamientoId == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"ID de alojamiento requerido\"}").build();
            }

            
            

            List<Reserva> reservas = ReservaDao.encontrarReservasPorAlojamiento(AlojamientoDao.encontrarAlojamientoPorId(Integer.parseInt(alojamientoId)));

            if (reservas == null || reservas.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"status\":\"ERROR\",\"message\":\"No se encontraron reservas para el alojamiento\"}").build();
            }

            JSONArray reservasJsonArray = new JSONArray();
            for (Reserva reserva : reservas) {
                JSONObject reservaJson = new JSONObject();
                reservaJson.put("fechaInicio", dateFormat.format(reserva.getFechaInicio()));
                reservaJson.put("fechaFin", dateFormat.format(reserva.getFechaFin()));
                reservaJson.put("reservaID", reserva.getReservaID());
                reservasJsonArray.put(reservaJson);
            }

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Información de las reservas obtenida correctamente.");
            jsonResponse.put("data", reservasJsonArray);

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
