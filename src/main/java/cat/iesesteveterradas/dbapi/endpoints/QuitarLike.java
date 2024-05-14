package cat.iesesteveterradas.dbapi.endpoints;

import org.hibernate.Session;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.Alojamiento;
import cat.iesesteveterradas.dbapi.persistencia.AlojamientoDao;
import cat.iesesteveterradas.dbapi.persistencia.SessionFactoryManager;
import cat.iesesteveterradas.dbapi.persistencia.Usuario;
import cat.iesesteveterradas.dbapi.persistencia.UsuarisDao;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/quitar/like")
public class QuitarLike {
    private static final Logger logger = LoggerFactory.getLogger(QuitarLike.class);

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
                        .entity("{\"status\":\"ERROR\",\"message\":\"ID de usuario requerido\"}").build();
            }
            if (alojamientoId == null || alojamientoId.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"status\":\"ERROR\",\"message\":\"ID de alojamiento requerido\"}").build();
            }

            Usuario usuario = UsuarisDao.encontrarUsuarioPorUserID(usuarioId);
            Alojamiento alojamiento = AlojamientoDao.encontrarAlojamientoPorId(Integer.parseInt(alojamientoId));

            if (usuario == null || alojamiento == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"status\":\"ERROR\",\"message\":\"Usuario o alojamiento no encontrado\"}").build();
            }

            Session session = SessionFactoryManager.getSessionFactory().openSession();
            usuario.unlikeAlojamiento(alojamiento);
            alojamiento.decrementLikes();
            session.update(alojamiento);

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Like agregado correctamente");

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
