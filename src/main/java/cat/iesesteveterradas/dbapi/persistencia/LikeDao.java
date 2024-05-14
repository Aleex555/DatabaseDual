package cat.iesesteveterradas.dbapi.persistencia;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LikeDao {
    private static final Logger logger = LoggerFactory.getLogger(LikeDao.class);

    public static Like crearLike(Usuario user, Alojamiento alojamiento) {

        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Transaction tx = null;
        Like like = null;

        try {
            tx = session.beginTransaction();
            like = new Like(user, alojamiento);
            session.save(like);
            tx.commit();
            logger.info("Nuevo like de: {}", like);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Error al crear el usuario", e);
        } finally {
            session.close();
        }
        return like;
    }

    public static void addLike(Usuario usuario, Alojamiento alojamiento) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Like like = new Like(usuario, alojamiento);
        session.persist(like);
        alojamiento.incrementLikes();
        session.saveOrUpdate(alojamiento);
    }

    public static void removeLike(Like like) {
        Session session = SessionFactoryManager.getSessionFactory().openSession();
        Alojamiento alojamiento = like.getAlojamiento();
        alojamiento.decrementLikes();
        session.delete(like);
        session.saveOrUpdate(alojamiento);
    }
}
