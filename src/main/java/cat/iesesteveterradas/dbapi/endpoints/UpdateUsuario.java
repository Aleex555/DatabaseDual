package cat.iesesteveterradas.dbapi.endpoints;

import java.util.Random;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.iesesteveterradas.dbapi.persistencia.ArchivoDrive;
import cat.iesesteveterradas.dbapi.persistencia.Propietario;
import cat.iesesteveterradas.dbapi.persistencia.PropietarioDao;
import cat.iesesteveterradas.dbapi.persistencia.Usuario;
import cat.iesesteveterradas.dbapi.persistencia.UsuarisDao;
import jakarta.ws.rs.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuari/update")
public class UpdateUsuario {
    private static final Logger logger = LoggerFactory.getLogger(UsuarisDao.class);
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuario(String jsonInput) {
        
        try {
            JSONObject input = new JSONObject(jsonInput);
            
            String email = input.optString("email", null);
            String nombre = input.optString("nombre", null);
            String telefono = input.optString("telefono", null);
            String base64 = input.optString("base64", null);
            String id = input.optString("id", null);
            


            // Validación para 'email'
            if (email == null || email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Email requerido\"}").build();
            }

            if (id == null || id.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Id requerido\"}").build();
            }

            if (nombre == null || nombre.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Contraseña requerida \"}").build();
            }

            if (telefono == null || telefono.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Telefono requerida \"}").build();
            }
            if (base64 == null || base64.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"ERROR\",\"message\":\"Base64 requerido \"}").build();
            }
            
            String url = "";
            byte[] data = Base64.getDecoder().decode(base64.toString());
            ;
            File tempFile = null;
            FileOutputStream fos = null;
            String fileNamePart = base64.length() > 7 ? base64.substring(0, 7) : base64;
            try {
                tempFile = File.createTempFile(fileNamePart, ".jpg");
                fos = new FileOutputStream(tempFile);
                fos.write(data);
                
                // Asegúrate de cerrar el stream
                fos.close();
                
                // Usar la clase ArchivoDrive para subir el archivo
                ArchivoDrive archivoDrive = new ArchivoDrive();
                url = archivoDrive.subirArchivoADrive(tempFile);
                System.out.println("Archivo subido. URL de visualización: " + url);
                
                
                tempFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (tempFile != null) {
                    tempFile.delete(); // Asegura que el archivo temporal se elimine incluso en caso de error.
                }
            }
            
            UsuarisDao.actualizarUsuario(id, nombre, email, telefono, url);


            Usuario user = UsuarisDao.encontrarUsuarioPorUserID(id);
            


            JSONObject jsonResponse = new JSONObject();

            JSONObject userData = new JSONObject();
            userData.put("id", user.getUserID());
            userData.put("gmail",user.getEmail());
            userData.put("telefono",user.getTelefono());
            userData.put("nombre",user.getNombre());
            userData.put("url", user.geturlFotoPerfil());
            jsonResponse.put("status", "OK");
            jsonResponse.put("message", "Credenciales actualizadas correctamente");
            

            // Añadir el objeto "data" al JSON de respuesta
            jsonResponse.put("data", userData);

            // Retorna la resposta
            String prettyJsonResponse = jsonResponse.toString(4); // 4 espais per indentar
            return Response.ok(prettyJsonResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("{\"status\":\"ERROR\",\"message\":\"Error en hacer el login\"}"+e).build();
        }
    }
}
