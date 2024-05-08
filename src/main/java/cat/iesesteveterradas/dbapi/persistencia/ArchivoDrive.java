package cat.iesesteveterradas.dbapi.persistencia;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

public class ArchivoDrive {
    public String subirArchivoADrive(java.io.File archivo) {
        try {
            @SuppressWarnings("deprecation")
            GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("/data/dual-421316-20d20209617c.json"))
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));

            Drive service = new Drive.Builder(new NetHttpTransport(), new GsonFactory(), credential)
                    .setApplicationName("Application Name")
                    .build();

            File fileMetadata = new File();
            fileMetadata.setName(archivo.getName());
            fileMetadata.setParents(Collections.singletonList("1P14lP-2kdntM7Oiug-sR5yKvhrp2PgET"));

            FileContent mediaContent = new FileContent("mimeType", archivo);
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            // Cambio de permisos
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            service.permissions().create(file.getId(), permission).execute();

            // Retorna URL de visualización
            return "https://drive.google.com/uc?export=view&id=" + file.getId();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // o manejar de otra manera
        }
    }
}
