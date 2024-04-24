package cat.iesesteveterradas.dbapi.persistencia;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlojaminetoDao {
    private static final Logger logger = LoggerFactory.getLogger(AlojaminetoDao.class);


    public static List<Alojamiento> encontrarAlojamientosPaginados(int page, int size) {
        List<Alojamiento> alojamientos = null;

        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            Query<Alojamiento> query = session.createQuery("FROM Alojamiento", Alojamiento.class);

            // Configurando la paginación
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            alojamientos = query.list();
            if (!alojamientos.isEmpty()) {
                logger.info("Se encontraron {} alojamientos en la página {} con tamaño {}", alojamientos.size(), page, size);
            } else {
                logger.info("No se encontraron alojamientos en la página {} con tamaño {}", page, size);
            }
        } catch (Exception e) {
            logger.error("Error al buscar alojamientos paginados para la página {} y tamaño {}", page, size, e);
        }
        return alojamientos;
    }

    public static Alojamiento encontrarAlojamientoPorId(int alojamientoId) {
        Alojamiento alojamiento = null;

        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            alojamiento = session.get(Alojamiento.class, alojamientoId);
            if (alojamiento != null) {
                logger.info("Se encontró el alojamiento con ID {}", alojamientoId);
            } else {
                logger.info("No se encontró ningún alojamiento con ID {}", alojamientoId);
            }
        } catch (Exception e) {
            logger.error("Error al buscar el alojamiento con ID {}", alojamientoId, e);
        }

        return alojamiento;
    }
}
