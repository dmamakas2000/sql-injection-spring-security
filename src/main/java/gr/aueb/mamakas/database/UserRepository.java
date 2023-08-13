package gr.aueb.mamakas.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Custom defined User repository interface.
 *
 * @author dimitrios.mamakas
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Retrieves all users from the database.
     */
    @Override
    List<User> findAll();

}
