package cat.iesesteveterradas.dbapi.persistencia;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;

public class PropietarioDao {
    private static final Logger logger = LoggerFactory.getLogger(PropietarioDao.class);

    @SuppressWarnings("deprecation")
    public static Propietario creaPropietario(String nombre, String email, String telefono, String contrasena) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Transaction tx = null;
        Propietario propietario = null;
        String hashedPassword = DigestUtils.sha256Hex(contrasena);

        try {
            tx = session.beginTransaction();
            propietario = new Propietario(nombre, email, telefono,hashedPassword);
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
        return propietario;
    }


    public static Propietario encontrarPropietarioPorEmailYContrasena(String email, String contrasena) {
        Propietario propietario = null;
        String hashedPassword = DigestUtils.sha256Hex(contrasena);
        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            propietario = session.createQuery("FROM Propietario WHERE emailContacto = :email AND contrasena = :contrasena", Propietario.class)
                    .setParameter("email", email)
                    .setParameter("contrasena", hashedPassword)
                    .uniqueResult();
            if (propietario != null) {
                logger.info("Propietario encontrado con el email: {}", email);
            } else {
                logger.info("No se encontró ningún propietario con el email: {} y contraseña proporcionada.", email);
            }
        } catch (Exception e) {
            logger.error("Error al buscar el propietario con email: {} y contraseña proporcionada.", email, e);
        }
        return propietario;
    }

    public static Propietario encontrarPropietarioPorID(int propietarioID) {
        Propietario propietario = null;
        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            propietario = session.createQuery("FROM Propietario WHERE propietarioID = :id", Propietario.class)
                    .setParameter("id", propietarioID)
                    .uniqueResult();
            if (propietario != null) {
                logger.info("Propietario encontrado con ID: {}", propietarioID);
            } else {
                logger.info("No se encontró ningún propietario con el ID: {}", propietarioID);
            }
        } catch (Exception e) {
            logger.error("Error al buscar el propietario con ID: {}", propietarioID, e);
        }
        return propietario;
    }

    public static Propietario encontrarPropietarioPorEmail(String email) {
        Propietario propietario = null;
        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            Query<Propietario> query = session.createQuery("FROM Propietario WHERE emailContacto = :email", Propietario.class);
            query.setParameter("email", email);
            propietario = query.uniqueResult();

            if (propietario != null) {
                logger.info("Propietario encontrado con el nombre: {}", email);
            } else {
                logger.info("No se encontró ningún propietario con el nombre: {}", email);
            }
        } catch (Exception e) {
            logger.error("Error al buscar el propietario con el nombre: {}", email, e);
        }
        return propietario;
    }
    
}
