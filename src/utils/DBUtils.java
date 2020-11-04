package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtils {
    private static final String PERSISTENCE_UNIT_NAME = "schedule_manager";
    private static EntityManagerFactory emf;

    public static EntityManager createEntityManager() {
        return _getEntityManagerFactory().createEntityManager();
    }

    private static EntityManagerFactory _getEntityManagerFactory() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }

        return emf;
    }
}
