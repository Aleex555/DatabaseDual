package cat.iesesteveterradas.dbapi.persistencia;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarisDao {

    private static final Logger logger = LoggerFactory.getLogger(UsuarisDao.class);

    public static Usuario creaUsuario(String nombre, String email, String telefono, String contrasena) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Transaction tx = null;
        Usuario usuario = null;
        String hashedPassword = DigestUtils.sha256Hex(contrasena);

        try {
            tx = session.beginTransaction();
            usuario = new Usuario(nombre, email, telefono,hashedPassword);
            session.save(usuario);
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
        return usuario;
    }


    public static Usuario encontrarUsuarioPorEmailYContrasena(String email, String contrasena) {
        Usuario usuario = null;
        String hashedPassword = DigestUtils.sha256Hex(contrasena);
        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            usuario = session.createQuery("FROM Usuario WHERE email = :email AND contrasena = :contrasena", Usuario.class)
                    .setParameter("email", email)
                    .setParameter("contrasena", hashedPassword)
                    .uniqueResult();
            if (usuario != null) {
                logger.info("Usuario encontrado con el email: {}", email);
            } else {
                logger.info("No se encontró ningún usuario con el email: {} y contraseña proporcionada.", email);
            }
        } catch (Exception e) {
            logger.error("Error al buscar el usuario con email: {} y contraseña proporcionada.", email, e);
        }
        return usuario;
    }
    
}
