package gr.aueb.mamakas.database;

import java.time.Instant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;

/**
 * Custom defined user insert into logging table repository. Tracks all the attempting authentication activity.
 *
 * @author dimitrios.mamakas
 *
 */
@Repository
@Setter
public class UserInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private boolean successfulLogin;

    /**
     * Inserts a log into the logging table.
     *
     * @param user User to insert into
     */
    @Transactional
    public void insertWithQuery( final User user ) {
        this.entityManager.createNativeQuery( "INSERT INTO logging (username, password, successful, date) VALUES (?,?,?,?)" )
                .setParameter( 1, user.getUsername() ).setParameter( 2, user.getPassword() ).setParameter( 3, this.successfulLogin )
                .setParameter( 4, Instant.now().toString() ).executeUpdate();
    }

}