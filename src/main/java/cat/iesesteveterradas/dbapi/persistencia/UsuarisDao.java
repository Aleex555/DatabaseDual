package cat.iesesteveterradas.dbapi.persistencia;

import java.util.HashSet;
import java.util.Set;

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
            usuario = new Usuario(nombre, email, telefono, hashedPassword);
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
            usuario = session
                    .createQuery("FROM Usuario WHERE email = :email AND contrasena = :contrasena", Usuario.class)
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

    public static Usuario encontrarUsuarioPorEmail(String email) {
        Usuario usuario = null;
        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            usuario = session.createQuery("FROM Usuario WHERE email = :email", Usuario.class)
                    .setParameter("email", email)
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

    public static Usuario encontrarUsuarioPorUserID(String userID) {
        Usuario usuario = null;
        try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
            usuario = session.createQuery("FROM Usuario WHERE userID = :userID", Usuario.class)
                    .setParameter("userID", userID)
                    .uniqueResult();
            if (usuario != null) {
                logger.info("Usuario encontrado con el userID: {}", userID);
            } else {
                logger.info("No se encontró ningún usuario con el userID: {}", userID);
            }
        } catch (Exception e) {
            logger.error("Error al buscar el usuario con userID: {}", userID, e);
        }
        return usuario;
    }

    @SuppressWarnings("deprecation")
    public static boolean actualizarUsuario(String userID, String nombre, String email, String telefono,
            String urlFotoPerfil) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Usuario usuario = encontrarUsuarioPorUserID(userID);
            if (usuario != null) {
                usuario.setNombre(nombre);
                usuario.setEmail(email);
                usuario.setTelefono(telefono);
                usuario.seturlFotoPerfil(urlFotoPerfil);
                session.update(usuario);
                tx.commit();
                logger.info("Usuario actualizado con éxito: {}", userID);
                return true;
            } else {
                logger.info("No se encontró el usuario: {}", userID);
                return false;
            }
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            logger.error("Error al actualizar el usuario con userID: {}", userID, e);
            return false;
        } finally {
            session.close();
        }
    }

    public static void likeAlojamiento(int userId, int alojamientoId) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Usuario usuario = session.get(Usuario.class, userId);
            Alojamiento alojamiento = session.get(Alojamiento.class, alojamientoId);
            if (usuario != null && alojamiento != null) {
                usuario.likeAlojamiento(alojamiento);
                session.saveOrUpdate(usuario);
                session.saveOrUpdate(alojamiento);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            logger.error("Error al dar like al alojamiento con ID: {} por el usuario ID: {}", alojamientoId, userId, e);
        } finally {
            session.close();
        }
    }

    public static void unlikeAlojamiento(int userId, int alojamientoId) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Usuario usuario = session.get(Usuario.class, userId);
            Alojamiento alojamiento = session.get(Alojamiento.class, alojamientoId);
            if (usuario != null && alojamiento != null && usuario.getAlojamientosLiked().contains(alojamiento)) {
                usuario.unlikeAlojamiento(alojamiento);
                session.saveOrUpdate(usuario);
                session.saveOrUpdate(alojamiento);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            logger.error("Error al quitar like del alojamiento con ID: {} por el usuario ID: {}", alojamientoId, userId,
                    e);
        } finally {
            session.close();
        }
    }

    public static Set<Alojamiento> obtenerLikesDeUsuario(int userId) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Set<Alojamiento> alojamientosLiked = new HashSet<>();
        try {
            Usuario usuario = session.get(Usuario.class, userId);
            if (usuario != null) {
                alojamientosLiked = usuario.getAlojamientosLiked();
            }
        } catch (Exception e) {
            logger.error("Error al obtener los alojamientos liked por el usuario con ID: {}", userId, e);
        } finally {
            session.close();
        }
        return alojamientosLiked;
    }

}
