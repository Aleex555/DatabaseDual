package cat.iesesteveterradas.dbapi.endpoints;

import cat.iesesteveterradas.dbapi.persistencia.Reserva;
import cat.iesesteveterradas.dbapi.persistencia.ReservaDao; // Asegúrate de tener esta implementación.
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.text.SimpleDateFormat;

@Path("/reserva")
public class InformacionReserva {
    private static final Logger logger = LoggerFactory.getLogger(InformacionReserva.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerInformacionReserva(String jsonInput) {
        try {
            JSONObject input = new JSONObject(jsonInput);
            String id = input.optString("reservaID", null);

            if (id == null || id.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"ID de reserva requerido\"}").build();
            }

            Reserva reserva = ReservaDao.encontrarReservaPorId(Long.parseLong(id));

            if (reserva == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"status\":\"ERROR\",\"message\":\"Reserva no encontrada\"}").build();
            }

            JSONObject reservaJson = new JSONObject();
            reservaJson.put("fechaInicio", dateFormat.format(reserva.getFechaInicio()));
            reservaJson.put("fechaFin", dateFormat.format(reserva.getFechaFin()));
            reservaJson.put("reservaID", reserva.getReservaID());

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Información de la reserva obtenida correctamente.");
            jsonResponse.put("data", reservaJson);

            return Response.ok(jsonResponse.toString()).build();
        } catch (Exception e) {
            logger.error("Error al obtener la información de la reserva", e);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "ERROR");
            errorResponse.put("message", "Error al obtener la información de la reserva: " + e.getMessage());
            return Response.serverError().entity(errorResponse.toString()).build();
        }
    }
}

