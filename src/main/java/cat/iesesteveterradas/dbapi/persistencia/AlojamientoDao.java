package cat.iesesteveterradas.dbapi.persistencia;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlojamientoDao {
    private static final Logger logger = LoggerFactory.getLogger(AlojamientoDao.class);

    public static Alojamiento crearAlojamiento(String nombre, String descripcion, String direccion, int capacidad, String reglas, double precioPorNoche, String urlFoto, int puntuaje, Propietario propietario) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Transaction tx = null;
        Alojamiento alojamiento = null;

        try {
            tx = session.beginTransaction();
            alojamiento = new Alojamiento(nombre, descripcion, direccion,capacidad,reglas,precioPorNoche,urlFoto,puntuaje,propietario);
            session.save(propietario);
            tx.commit();
            logger.info("Nuevo usuario creado con el nickname: {}", nombre);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Error al crear el usuario", e);
        } finally {
            session.close();
        }
        return alojamiento;
    }

    public static List<Alojamiento> encontrarAlojamientosPorPropietarioPaginados(long propietarioID, int page, int size) {
        List<Alojamiento> alojamientos = null;

        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            Query<Alojamiento> query = session.createQuery("FROM Alojamiento a WHERE a.propietario.propietarioID = :propietarioID", Alojamiento.class);
            query.setParameter("propietarioID", propietarioID);

            // Configurando la paginación
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            alojamientos = query.list();
            if (!alojamientos.isEmpty()) {
                logger.info("Se encontraron {} alojamientos para el propietario con ID {} en la página {} con tamaño {}", alojamientos.size(), propietarioID, page, size);
            } else {
                logger.info("No se encontraron alojamientos para el propietario con ID {} en la página {} con tamaño {}", propietarioID, page, size);
            }
        } catch (Exception e) {
            logger.error("Error al buscar alojamientos paginados para el propietario con ID {} en la página {} y tamaño {}", propietarioID, page, size, e);
        }
        return alojamientos;
    }

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

    @SuppressWarnings("deprecation")
public static boolean actualizarAlojamiento(int alojamientoId, String descripcion, String nombre, String direccion, String capacidad, String reglas, String precioPorNoche, String urlFoto) {
    Session session = SessionFactoryManager.getSessionFactory().openSession();
    Transaction tx = null;
    try {
        tx = session.beginTransaction();
        Alojamiento alojamiento = encontrarAlojamientoPorId(alojamientoId);
        if (alojamiento != null) {
            alojamiento.setDescripcion(descripcion);
            alojamiento.setNombre(nombre);
            alojamiento.setDireccion(direccion);
            alojamiento.setCapacidad(Integer.parseInt(capacidad));
            alojamiento.setReglas(reglas);
            alojamiento.setPrecioPorNoche(Double.parseDouble(precioPorNoche));
            alojamiento.setUrlFoto(urlFoto);
            session.update(alojamiento);
            tx.commit();
            logger.info("Alojamiento actualizado con éxito: {}", alojamientoId);
            return true;
        } else {
            logger.info("No se encontró el alojamiento: {}", alojamientoId);
            return false;
        }
    } catch (Exception e) {
        if (tx != null) tx.rollback();
        logger.error("Error al actualizar el alojamiento con ID: {}", alojamientoId, e);
        return false;
    } finally {
        session.close();
    }
}

}
