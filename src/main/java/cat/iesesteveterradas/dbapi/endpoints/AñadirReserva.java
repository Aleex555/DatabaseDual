package cat.iesesteveterradas.dbapi.endpoints;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.AlojamientoDao;
import cat.iesesteveterradas.dbapi.persistencia.Reserva;
import cat.iesesteveterradas.dbapi.persistencia.ReservaDao;
import cat.iesesteveterradas.dbapi.persistencia.UsuarisDao;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/reservas/anadir")
public class AñadirReserva {
     private static final Logger logger = LoggerFactory.getLogger(InformacionReserva.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasPorAlojamiento(String jsonInput) {
        try {
            JSONObject input = new JSONObject(jsonInput);
            String alojamientoId = input.optString("alojamientoID");
            String fechainicio = input.optString("fechainicio");
            String fechafinal = input.optString("fechafinal");
            String total = input.optString("total");
            String usuario = input.optString("usuarioID");

            if (alojamientoId == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"ID de alojamiento requerido\"}").build();
            }
            if (fechafinal == null || fechafinal.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Fecha Final requerida\"}").build();
            }

            if (fechainicio == null || fechainicio.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Fecha Inicio requerida \"}").build();
            }

            if (total == null || total.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Total requerido \"}").build();
            }
            if (usuario == null || usuario.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Id usuario requerido \"}").build();
            }


            ReservaDao.crearReserva(UsuarisDao.encontrarUsuarioPorUserID(usuario), AlojamientoDao.encontrarAlojamientoPorId(Integer.parseInt(alojamientoId)), fechainicio, fechafinal, Double.parseDouble(total));

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
