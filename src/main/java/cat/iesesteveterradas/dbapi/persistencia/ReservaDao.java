package cat.iesesteveterradas.dbapi.persistencia;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;

public class ReservaDao {
    private static final Logger logger = LoggerFactory.getLogger(ReservaDao.class);

    public static Reserva crearReserva(Propietario propietario, Alojamiento alojamiento, Date fechaInicio, Date fechaFin, double total) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Transaction tx = null;
        Reserva reserva = null;

        try {
            tx = session.beginTransaction();
            reserva = new Reserva();
            reserva.setPropietario(propietario);
            reserva.setAlojamiento(alojamiento);
            reserva.setFechaInicio(fechaInicio);
            reserva.setFechaFin(fechaFin);
            reserva.setTotal(total);
            session.save(reserva);
            tx.commit();
            logger.info("Reserva creada con éxito para el usuario: {}", propietario.getPropietarioID());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Error al crear la reserva", e);
        } finally {
            session.close();
        }
        return reserva;
    }

    public static List<Reserva> encontrarReservasPorUsuario(long usuarioID, int page, int size) {
        List<Reserva> reservas = null;

        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            Query<Reserva> query = session.createQuery("FROM Reserva r WHERE r.usuario.usuarioID = :usuarioID", Reserva.class);
            query.setParameter("usuarioID", usuarioID);

            // Configuración de paginación
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            reservas = query.list();
            if (!reservas.isEmpty()) {
                logger.info("Se encontraron {} reservas para el usuario con ID {} en la página {} con tamaño {}", reservas.size(), usuarioID, page, size);
            } else {
                logger.info("No se encontraron reservas para el usuario con ID {} en la página {} con tamaño {}", usuarioID, page, size);
            }
        } catch (Exception e) {
            logger.error("Error al buscar reservas paginadas para el usuario con ID {} en la página {} y tamaño {}", usuarioID, page, size, e);
        }
        return reservas;
    }

    public static List<Reserva> encontrarReservasPorAlojamiento(Alojamiento alojamiento) {
        List<Reserva> reservas = null;
        if (alojamiento == null || alojamiento.getAlojamientoID() == null) {
            logger.error("El alojamiento proporcionado es nulo o no tiene un ID válido.");
            return reservas; // Retorna null para indicar un problema con los parámetros de entrada.
        }

        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            String hql = "FROM Reserva r WHERE r.alojamiento = :alojamiento";
            Query<Reserva> query = session.createQuery(hql, Reserva.class);
            query.setParameter("alojamiento", alojamiento);
            reservas = query.list();
            if (!reservas.isEmpty()) {
                logger.info("Se encontraron {} reservas para el alojamiento con ID {}", reservas.size(), alojamiento.getAlojamientoID());
            } else {
                logger.info("No se encontraron reservas para el alojamiento con ID {}", alojamiento.getAlojamientoID());
            }
        } catch (Exception e) {
            logger.error("Error al buscar las reservas para el alojamiento con ID {}", alojamiento.getAlojamientoID(), e);
        }
        return reservas;
    }
}